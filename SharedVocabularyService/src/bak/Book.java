package bak;

import thewebsemantic.Id;
import thewebsemantic.Namespace;

@Namespace("http://example.org/")
public class Book {
  private String isbn;
  private String title;

  public Book(String isbn) {
    this.isbn = isbn;
  }

  @Id
  public String isbn() {return isbn;}
  
  public void setTitle(String s) {title = s;}
  public String getTitle() { return title;}
  
}