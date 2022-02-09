package edd2_trab2;

import edd2_trab2.structures.*;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

class MyString implements Comparable<MyString> {
 private String content;
  private static int countCompares = 0;
  public MyString( String content ){ this.content = content; }
  public static void resetCompares() { countCompares = 0; }
  public String getContent() { return content; }
  public String toString() { return content; }
  public static int getCompares() { return countCompares; }
  public int hashCode(){ return content.hashCode(); }
  public int compareTo(MyString s) {
    countCompares++;
    return content.compareTo( s.getContent() );
  }
}
class Main {
  private static boolean DEBUG = true;
  private static boolean MEASURE = true;
  // se DEBUG && !MEASURE, o programa deve
  // gerar como resultado impresso o conteúdo
  // do arquivo debug.txt
  // se !DEBUG && MEASURE, o programa irá
  // imprimir a quantidade de comparações
  // média em um acesso do tipo hit (acesso a
  // uma chave presente na estrutura de
  // dados), em função da quantidade de nós
  // armazenados, calculada de maneira
  // experimental
  private static int MIN_SKIP = 4;
  private static int MAX_SKIP = 128;

  public static void main(String[] args) {
      TextReader leitor = new TextReader();
      String texto = leitor.textRead("tale.txt");
      System.out.println(texto);
  }
      /*
    HashTable<MyString, Integer> data
        = new HashTable<>(233);
        // = new HashTable<>(193939);
        // = new HashTable<>(5308417);
    Scanner sc;
    try {
      sc = new Scanner(new File("tale.txt"));
      int thresh = 8;

      if( MEASURE ) {
        System.out.println( "<size> <number of comparisons (avg)>" );
      }
      while (sc.hasNext()) {
        MyString word = new MyString( sc.next() );
        
        Integer count = data.get( word );
        if( count != null )
          data.put( word, count.intValue() + 1 );
        else
          data.put( word, 1 );

        if( data.size() == thresh && (DEBUG || MEASURE) ) {
          if( DEBUG )
            System.out.print( data.size() + " palavras distintas – ");
          if( MEASURE ) MyString.resetCompares();

          int countTotal = 0;
          Iterable<MyString> allKeys = data.keys();
          for( MyString key : allKeys )
            countTotal += data.get(key).intValue();

          if( DEBUG )
            System.out.println( countTotal + " palavras totais");
          if( MEASURE )
            System.out.println( data.size() +  " " + 1.0*MyString.getCompares()/data.size() );
          if( thresh >> 4 < MIN_SKIP )
            thresh += MIN_SKIP;
          else if ( thresh >> 4 > MAX_SKIP )
            thresh += MAX_SKIP;
          else thresh += (0x80000000 >>> (Integer.numberOfLeadingZeros(thresh) + 4));
        }
      }

      if( DEBUG || MEASURE ) {
        if( DEBUG )
          System.out.print( "TOTAL: " + data.size() + " palavras distintas – ");
        if( MEASURE ) MyString.resetCompares();

        int countTotal = 0;
        Iterable<MyString> allKeys = data.keys();
        for( MyString key : allKeys )
          countTotal += data.get(key).intValue();

        if( DEBUG )
          System.out.println( countTotal + " palavras totais");
        if( MEASURE )
          System.out.println( data.size() +  " " + 1.0*MyString.getCompares()/data.size() );
      }
    }
    catch( FileNotFoundException e ) {
      System.out.println( "Arquivo não encontrado!" );
    }
  }*/
}