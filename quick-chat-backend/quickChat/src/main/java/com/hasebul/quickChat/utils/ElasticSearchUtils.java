package com.hasebul.quickChat.utils;

import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;

public class ElasticSearchUtils {

    public static TermQuery chatMessageTermQuery(String field, String value){
        TermQuery.Builder query = new TermQuery.Builder();
        return query.field(field).value(value).build();
    }
}
