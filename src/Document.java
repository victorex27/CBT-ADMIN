
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class Document {
    
    private File file;
    private String fileName;
    private double fileSize;
    private String fileExtension;
    
    public Document getFile(int id) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException{

        Connection connection = SimpleConnection.getConnection();

        
        String sqlQuery = " SELECT * from document where id =  ?";
        
        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);
        
        pStatement.setInt(id, id);
        
        ResultSet result = pStatement.executeQuery();
        
        while( result.next() ){
        
            fileName = result.getString("name");
            fileExtension = result.getString("type");
            
            InputStream in = result.getBinaryStream("file");
            OutputStream out = new FileOutputStream( new File(fileName+"."+fileExtension) );
            
            byte[] content = new byte[1024];
            
            int size = 0;
            
            //while(in.read(content) > 0){ out.write(content);}
        
            while((size = in.read(content)) != -1){
            
                out.write(content,0,size);
            }
            in.close();
            out.close();
        }
        
        
        connection.close();
    
        return null;
    }

    public File getBlob() {
        return file;
    }

    public String getFileName() {
        return fileName;
    }

    public double getFileSize() {
        return fileSize;
    }

    public String getFileExtension() {
        return fileExtension;
    }
    
}
