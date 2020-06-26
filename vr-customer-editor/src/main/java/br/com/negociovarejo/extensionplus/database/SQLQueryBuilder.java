/* * Copyright (c) 2020 Negocio Varejo. All rights reserved. * */
package br.com.negociovarejo.extensionplus.database;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author derickfelix
 */
public class SQLQueryBuilder {

    private static SQLQueryBuilder builder;
    private final List<Table> tables;
    private String with;

    private SQLQueryBuilder(String[] fields)
    {
        this.tables = new ArrayList<>();

        Table table = new Table();
        table.fields = fields;
        tables.add(table);
    }

    public static SQLQueryBuilder select(String... fields)
    {
        builder = new SQLQueryBuilder(fields);

        return builder;
    }

    public SQLQueryBuilder from(String tableName, String alias)
    {
        Table table = tables.get(0);
        table.name = tableName;
        table.alias = alias;

        return this;
    }

    public SQLQueryBuilder innerJoin(String tableName, String alias, String on, String... fields)
    {
        Table table = new Table();
        table.name = tableName;
        table.alias = alias;
        table.fields = fields;
        table.on = on;
        tables.add(table);

        return this;
    }

    public SQLQueryBuilder with(String with)
    {
        this.with = with;

        return this;
    }

    public String build()
    {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");

        boolean all = true;

        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);

            if (table.fields.length != 0) {
                all = false;
                break;
            }
        }

        if (all) {
            sql.append("* ");
        } else {
            for (int i = 0; i < tables.size(); i++) {
                Table table = tables.get(i);

                if (tables.size() == i + 1) {
                    fillWithFields(table, sql, true);
                } else {
                    fillWithFields(table, sql, false);
                }
            }
        }

        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);

            if (i == 0) {
                sql.append("from ")
                        .append(table.name)
                        .append(' ')
                        .append(table.alias);
            } else {
                sql.append(" inner join ")
                        .append(table.name)
                        .append(' ')
                        .append(table.alias)
                        .append(" on ")
                        .append(table.on);
            }
        }

        if (with != null) {
            sql.append(" where ").append(with);
        }

        return sql.toString();
    }

    private void fillWithFields(Table table, StringBuilder sql, boolean last)
    {
        String[] fields = table.fields;

        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];

            sql.append(table.alias)
                    .append('.')
                    .append(field);

            if (last && fields.length == i + 1) {
                sql.append(" ");
            } else {
                sql.append(", ");
            }
        }
    }

    private class Table {

        private String name;
        private String alias;
        private String[] fields;
        private String on;

    }
}
