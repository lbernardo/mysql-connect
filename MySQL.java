import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author lucas
 */
public class MySQL {
    public String urlMySQL;
    public String bancoMySQL;
    public String userMySQL;
    public String passMySQL;
    Connection connMySQL;
    ResultSet rsMySQL;
    Statement stmMySQL;
    PreparedStatement resultPrepared;
    public MySQL(String local,String user,String pass,String db){
        
        userMySQL = user;
        passMySQL = pass;
        bancoMySQL = db;
        
        urlMySQL = "jdbc:mysql://"+local+":3306/"+bancoMySQL;
        try 
        {
                 Class.forName("com.mysql.jdbc.Driver").newInstance();
                 connMySQL = DriverManager.getConnection(urlMySQL, userMySQL, passMySQL);
                 try{
                   System.out.println("Conectado!");
                 }
                 catch (Exception ex){
                     System.out.println("Erro na conexão");
                 }
        }
        catch (Exception ex) 
         {
              System.out.println("Sem conexão!");
         }   
        
        
    }
    
    /**
     * Prepara SQL 
     * @param sql
     * @return PreparedStatement
     */
    public PreparedStatement  prepare(String sql){
        try{
            resultPrepared = connMySQL.prepareStatement(sql);
            return resultPrepared;
        }catch(SQLException e){
            System.out.println("Erro no SQL:"+e);
            return null;
        }        
    }
    
    
    /**
     * Realiza SQlQuery retornando identificador
     * @param sql
     * @return ResultSet
     */
    public MySQL query(String sql){
        // Se for ok
        try {
            stmMySQL = connMySQL.createStatement();
        }
        // Se não
        catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Começa a executar o query
       try{
       
           rsMySQL = stmMySQL.executeQuery(sql);
           // Se for ok
          try{
           return this;
          }
          // Se não
          catch(Exception ex){
             System.out.println("Erro no SQL:"+ex);  
          }
       
       }
       // Se não foi bem executado o query
       catch(Exception ex){
         System.out.println("Erro no SQL:"+ex);  
       }
       
       return null;
    }
    
    /**
     * Retorna o número de linhas
     * @return inteiro
     */
    public int rows(){
        int rows = 0;
        try{
            return rsMySQL.getMetaData().getColumnCount();
        }catch(SQLException e){
            System.out.println("Erro no pedido de SQL:"+e);
        }
        return rows;
    }
    
    /**
     * Pula uma linha
     * @return boolean
     */
    public boolean next(){
        try{
            return rsMySQL.next();
        }catch(SQLException e){
            System.out.println("Erro ao retornar valores:"+e);
            return false;
        }
    }
    
    
    public int get_id(){
        MySQL e = this.query("SELECT LAST_INSERT_ID() as id");
        e.next();
        String id = e.get("id");
        return new Integer(id).intValue();
    }
    
    
    /**
     * Retorna a coluna
     * @param g
     * @return String 
     */
    public String get(String g){
        try{
            return rsMySQL.getString(g);
        }catch(SQLException e){
            return null;
        }
    }
    
}


