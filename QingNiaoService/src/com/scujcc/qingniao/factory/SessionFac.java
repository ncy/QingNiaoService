package com.scujcc.qingniao.factory;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SessionFac {
   private static SqlSession sqlSession ;
   public static SqlSession getSession(){
	   String resource = "com/scujcc/qingniao/config/configuration.xml";  
	        Reader reader = null;  
       try{  
	               reader = Resources.getResourceAsReader(resource);  
	            }catch(IOException e)  
	                {  
	               e.printStackTrace();  
	              }  
	                
	     //创建SqlSessionFactory实例。没有指定要用到的  
	      //environment，则使用默认的environment  
	      SqlSessionFactory sqlSessionFactory  
	         = new SqlSessionFactoryBuilder().build(reader);  
            
       sqlSession = sqlSessionFactory.openSession(); 

	   return sqlSession;
   }
}
