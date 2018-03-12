package com.mycompany.app;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LuceneEmbededController {

    @Autowired
    private IndexSearcher searcher;

    @GetMapping(value = "/lucene/search")
    public String  search(@RequestParam String searchText) throws Exception {

        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser("contents", analyzer);
        parser.setAllowLeadingWildcard(true);
        Query query = parser.parse(searchText);
        ScoreDoc[] hits = searcher.search(query, 1000).scoreDocs;
        // Iterate through the results:
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document hitDoc = searcher.doc(docId);
            sb.append(hitDoc.get("contents"));
            sb.append("<br/>");
        }
        return sb.toString();
    }
}
