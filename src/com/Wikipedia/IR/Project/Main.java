package com.Wikipedia.IR.Project;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import java.util.Scanner;

public class Main {
	
	   String indexDir = "D:\\Projects\\Java\\Wikipedia Search Engine\\Lucene\\Index";
	   String dataDir = "D:\\Projects\\Java\\Wikipedia Search Engine\\Lucene\\Data";
	   Indexer indexer;
	   Searcher searcher;
	   static Scanner in=new Scanner(System.in);
	   static String Query;
	   public static void main(String[] args) {
	      Main run;
	      try {
	         run = new Main();
	         run.createIndex();
	         System.out.println("Enter Query:-");
	         Query=in.nextLine();
	         run.search(Query);
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (ParseException e) {
	         e.printStackTrace();
	      }
	   }

	   private void createIndex() throws IOException{
	      indexer = new Indexer(indexDir);
	      int numIndexed;
	      long startTime = System.currentTimeMillis();	
	      numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
	      long endTime = System.currentTimeMillis();
	      indexer.close();
	      System.out.println(numIndexed+" File indexed, time taken: "
	         +(endTime-startTime)+" ms");		
	   }

	   private void search(String searchQuery) throws IOException, ParseException{
	      searcher = new Searcher(indexDir);
	      long startTime = System.currentTimeMillis();
	      TopDocs hits = searcher.search(searchQuery);
	      long endTime = System.currentTimeMillis();
	   
	      System.out.println(hits.totalHits +
	         " documents found. Time :" + (endTime - startTime));
	      for(ScoreDoc scoreDoc : hits.scoreDocs) {
	         Document doc = searcher.getDocument(scoreDoc);
	            System.out.println("File: "
	            + doc.get(LuceneConstants.FILE_PATH)+" Score: " +scoreDoc.score);
	      }
	      searcher.close();
	   }
	}