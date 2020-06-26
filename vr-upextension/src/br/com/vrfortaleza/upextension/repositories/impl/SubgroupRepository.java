/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.repositories.impl;

import br.com.vrfortaleza.upextension.database.RowMapper;
import br.com.vrfortaleza.upextension.database.VRDatabaseTemplate;
import br.com.vrfortaleza.upextension.models.Subgroup;
import br.com.vrfortaleza.upextension.models.Mercadologico;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author derickfelix
 */
public class SubgroupRepository {

    private final VRDatabaseTemplate template;

    public SubgroupRepository()
    {
        this.template = new VRDatabaseTemplate();
    }

    /**
     * Queries for a {@code mercadologico} for a given dot-separated
     * identification number, the last three numbers is the section, group, and
     * subgroup respectively.
     *
     * @param num is the identification number, e.g. {@code 023.034.001.001}
     * @return a subgroup object
     */
    public Subgroup find(String num)
    {
        // Split ' - ' before in case of product containing dots (.)
        // e.g. (035.005.006.008 - AMACIANTES ACIMA DE 1.5L) -> (1.5L)
        String[] sg = num.split(" - ");
        String[] ms = sg[0].split("\\.");

        if (ms.length < 3) {
            throw new IllegalArgumentException("The mercadologico length is less than three.");
        }

        String sql = "select * from mercadologico where ";
        Map<String, Object> params = new HashMap<>();
        // section level
        int slevel = 1;
        sql += "mercadologico1 = :m1";
        params.put("m1", ms[0]);

        if (ms.length > 3) {
            sql += " and mercadologico2 = :m2";
            params.put("m2", ms[1]);
            slevel = 2;
        }

        if (ms.length > 4) {
            sql += " and mercadologico3 = :m3";
            params.put("m3", ms[2]);
            slevel = 3;
        }
        List<Mercadologico> mls = template.queryForList(sql, params, new MercadologicoMapper());
        Subgroup subgroup = new Subgroup();
        // subgroup is always the last m number
        subgroup.setName(Integer.parseInt(ms[ms.length - 1]) + " - " + sg[1]);
        int group = Integer.parseInt(ms[ms.length - 2]);
        switch (slevel) {
            case 1:
                for (Mercadologico ml : mls) {
                    if (ml.getMercadologico2() == 0 && ml.getMercadologico3() == 0) {
                        subgroup.setSection(ml.getMercadologico1() + " - " + ml.getDescription());
                    } else if (ml.getMercadologico2() == group && ml.getMercadologico3() == 0) {
                        subgroup.setGroup(ml.getMercadologico2() + " - " + ml.getDescription());
                    }

                    if (subgroup.getSection() != null && subgroup.getGroup() != null) {
                        break;
                    }
                }
                break;
            case 2:
                for (Mercadologico ml : mls) {
                    if (ml.getMercadologico3() == 0 && ml.getMercadologico4() == 0) {
                        subgroup.setSection(ml.getMercadologico2() + " - " + ml.getDescription());
                    } else if (ml.getMercadologico3() == group && ml.getMercadologico4() == 0) {
                        subgroup.setGroup(ml.getMercadologico3() + " - " + ml.getDescription());
                    }
                    
                    if (subgroup.getSection() != null && subgroup.getGroup() != null) {
                        break;
                    }
                }
                break;
            case 3:
                for (Mercadologico ml : mls) {
                    if (ml.getMercadologico4() == 0 && ml.getMercadologico5() == 0) {
                        subgroup.setSection(ml.getMercadologico3() + " - " + ml.getDescription());
                    } else if (ml.getMercadologico4() == group && ml.getMercadologico5() == 0) {
                        subgroup.setGroup(ml.getMercadologico4() + " - " + ml.getDescription());
                    }
                    
                    if (subgroup.getSection() != null && subgroup.getGroup() != null) {
                        break;
                    }
                }
                break;
        }

        return subgroup;
    }

    private class MercadologicoMapper implements RowMapper<Mercadologico> {

        @Override
        public Mercadologico mapRow(ResultSet rs) throws SQLException
        {
            Mercadologico m = new Mercadologico();
            m.setMercadologico1(rs.getInt("mercadologico1"));
            m.setMercadologico2(rs.getInt("mercadologico2"));
            m.setMercadologico3(rs.getInt("mercadologico3"));
            m.setMercadologico4(rs.getInt("mercadologico4"));
            m.setMercadologico5(rs.getInt("mercadologico5"));
            m.setDescription(rs.getString("descricao"));
            return m;
        }

    }

}
