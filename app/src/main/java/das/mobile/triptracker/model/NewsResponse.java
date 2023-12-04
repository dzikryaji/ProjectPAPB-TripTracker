package das.mobile.triptracker.model;

import java.util.ArrayList;

public class NewsResponse {
 private int totalResult;
 private String status;
 private ArrayList<News> articles;

 public NewsResponse(int totalResult, String status, ArrayList<News> articles) {
  this.totalResult = totalResult;
  this.status = status;
  this.articles = articles;
 }

 public int getTotalResult() {
  return totalResult;
 }

 public void setTotalResult(int totalResult) {
  this.totalResult = totalResult;
 }

 public String getStatus() {
  return status;
 }

 public void setStatus(String status) {
  this.status = status;
 }

 public ArrayList<News> getArticles() {
  return articles;
 }

 public void setArticles(ArrayList<News> articles) {
  this.articles = articles;
 }
}
