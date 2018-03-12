package com.mycompany.app;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.io.*;
import java.net.ServerSocket;
import java.nio.file.Paths;

@Configuration
public class AppConfig {

    @Bean("h2")
    @Primary
    public EmbeddedDatabase dataSource(){

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        long startTime = System.currentTimeMillis();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.H2) //.H2 or .DERBY
                .addScript("db/sql/create.sql")
                .addScript("db/sql/importsmall.sql")
                .build();
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("The total time to load H2:"+ elapsedTime);
        return db;
    }

    @Bean("sqllite")
    public DataSource dataSourceSQLLite() throws Exception{
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.type(org.sqlite.SQLiteDataSource.class);
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:mds.db");
        long startTime = System.currentTimeMillis();


        DataSource ds = dataSourceBuilder.build();
        Resource resource = new ClassPathResource("db/sql/create-sqllite-fts.sql");
        Resource resourceInsert = new ClassPathResource("db/sql/importsmall.sql");
        ScriptUtils.executeSqlScript(ds.getConnection(),resource );
        long elapsedTime = System.currentTimeMillis() - startTime;
        ScriptUtils.executeSqlScript(ds.getConnection(),resourceInsert );
        System.out.println("The total time to load sqlite:"+ elapsedTime);
        return ds;
    }

    @Bean
    public IndexSearcher getDirectory() throws Exception{
        Analyzer analyzer = new StandardAnalyzer();
        Directory directory = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);

        String path = getClass().getClassLoader().getResource("lucene/export-local.csv").getPath();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String thisLine;
        int counter = 0;
        while ((thisLine = br.readLine()) != null) {
            Document doc = new Document();
            doc.add(new StringField("contents", thisLine, Field.Store.YES));
            iwriter.addDocument(doc);
            System.out.println("Loading Line " + counter++);
           /* if(counter > 1000){
                break;
            }*/
        }

        iwriter.close();
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
   }


}
