package com.hasebul.quickChat.utils;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.hasebul.quickChat.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Supplier;

public class ElasticSearchUtils {

    public static List<Query> chatMessageTermQuery(String senderId, String receiverId){
        List<Query> queries = new ArrayList<>();
        TermQuery.Builder termQuery1 = new TermQuery.Builder();
        queries.add(Query.of(q->q.term(termQuery1.field("senderId").value(senderId).build())));
        TermQuery.Builder termQuery2 = new TermQuery.Builder();
        queries.add(Query.of(q->q.term(termQuery2.field("receiverId").value(receiverId).build())));
        return queries;
    }

    public static BoolQuery boolQueryDsl(String senderId, String receiverId){
        BoolQuery.Builder query = new BoolQuery.Builder();
        return query.must(chatMessageTermQuery(senderId, receiverId)).build();
    }

    public static Supplier<Query> chatMessageSupplier(String senderId, String receiverId){
        return ()-> Query.of(q->q.bool(boolQueryDsl(senderId,receiverId)));
    }
}
