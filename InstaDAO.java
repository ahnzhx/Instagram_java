package ig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

//import gui_mysql.MemberDTO;
import ig.DBCon;

public class InstaDAO {
	private Connection con=null;
	private PreparedStatement ps=null;
	ResultSet rs;
	InstaDTO dto=new InstaDTO();
	int cmd=0;
	public static final int NONE=0;
	public static final int ADD=1;
	public static final int DEL=2;
	public static final int FIND=3;
	public static final int ALL=4;
	
	public InstaDAO(){
		con=DBCon.getConnection();
		System.out.println("Db연결:"+con);
	}
	
	
	public int insertMember(InstaDTO dto) throws SQLException{
		String sql="insert into insta VALUES(null,?,?,?,?,?)";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getPw());
			ps.setString(3, dto.getBirth());
			ps.setString(4, dto.getGender());
			ps.setString(5, dto.getNation());
			int n=ps.executeUpdate();
			return n;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}finally{
			DBCon.close(ps);
		}
	}
	
	public String searchPw(String id){
		try {
			String sql="select pw from insta where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1,id);
			rs=ps.executeQuery();
			String ma=makeArray(rs);
			
			return ma;
		} catch (Exception e) {
			return null;
		}finally{
			DBCon.close(rs);
			DBCon.close(ps);
		}
	}
	
	public String makeArray(ResultSet rs) throws SQLException{
		String pw="";
		while(rs.next()){
			pw=rs.getString("pw");
		}
		return pw;
	}
	
	public InstaDTO login2 (String id){
		InstaDTO arr2=null;
		try {
			String sql="Select id,pw from insta where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			arr2=makeArray02(rs);
			return arr2;
		} catch (Exception e) {
			return null;
		}finally{
			DBCon.close(rs);
			DBCon.close(ps);
		}
	}
		
	public InstaDTO makeArray02(ResultSet rs) throws SQLException{
		InstaDTO dto3 = null;
		while(rs.next()){
			String id2="";
			id2=rs.getString("id");
			String pw2="";
			pw2=rs.getString("pw");
			dto3=new InstaDTO(id2,pw2);
		}
		
		return dto3;
		}
	
	public String login3 (String id){
		String arr2=null;
		try {
			String sql="Select id from insta where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			arr2=makeArray03(rs);
			return arr2;
		} catch (Exception e) {
			return null;
		}finally{
			DBCon.close(rs);
			DBCon.close(ps);
		}
	}	
	public String makeArray03(ResultSet rs) throws SQLException{
		String id2="";
		while(rs.next()){
			id2=rs.getString("id");
		}
		return id2;//null媛믪씠 �븘�땲怨� 珥덇린媛믪씤 ""�쑝濡� 諛섑솚�맂�떎. 
		}
	
	
	///////////////////////////////////////////////////
	public InstaDTO[] selectAll(){
		try {
			String sql="SELECT * FROM insta";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			InstaDTO[] arr=makeArray04(rs);
			return arr;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBCon.close(rs);
			DBCon.close(ps);
		}
	}
	
	public InstaDTO[] selectByName(String id,int var) {
		String sql="";
		try {
		if(var==1){sql="SELECT * FROM insta WHERE id=?";}
		else if(var==2){sql="SELECT * FROM insta WHERE gender=?";}
		else if(var==3){sql="SELECT * FROM insta WHERE nation=?";} 
			ps=con.prepareStatement(sql);
			ps.setString(1, id);
			System.out.println(id);
			rs=ps.executeQuery();
			InstaDTO[] arr=makeArray04(rs);
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			DBCon.close(rs);
			DBCon.close(ps);
		}
	}
	


	//ResultSet�뿉�꽌 �뜲�씠�꽣 爰쇰궡�� MemberDTO[]諛곗뿴�뿉 ���옣�빐 �룎�젮二쇰뒗 硫붿냼�뱶
	public InstaDTO[] makeArray04(ResultSet rs) throws SQLException {
		//而щ젆�뀡�쓣 �뜥�빞�븷 �븣 vector�꽑�뼵
		Vector<InstaDTO> v=new Vector<InstaDTO>();
		switch(cmd){
		case NONE:
			while(rs.next()) {
				String no=rs.getString("no");
				String id=rs.getString("id");
				String pw=rs.getString("pw");
				String bd=rs.getString("birth");
				String gd=rs.getString("gender");
				String nation=rs.getString("nation");
				//�븳媛쒖쓽 �젅肄붾뱶瑜� DTO媛앹껜濡� 留뚮벉
				InstaDTO dto=new InstaDTO(no,id,pw,bd,gd,nation);
				v.add(dto);		
			}
			break;
		}
	InstaDTO[] memArr=new InstaDTO[v.size()];
	v.copyInto(memArr);
	return memArr;
	}

	
	public String[][] makeArray05(ResultSet rs,String var3) throws SQLException{
		String[][] v= new String [10][2];
		while(rs.next()) {
			String gd=rs.getString(var3);
			System.out.println(gd);
			String total=rs.getInt("count(*)")+"";
			for(int i=0;i<10;i++){
			v[i][0]=gd;
			v[i][1]=total;
			}
		}
	return v;
	}

	public String[][] stat(String var) throws SQLException{
		String sql="";
		String var2="gender";
		String[][] arr=null;
		try {
			sql="SELECT gender,count(*) FROM insta WHERE gender=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, var);
			rs=ps.executeQuery();
			arr=makeArray05(rs,var2);
			System.out.println(arr);
			return arr;
		}finally { DBCon.close(ps);}
	}
	
	public String[][] stat2(String var) throws SQLException{
		String sql="";
		String var2="nation";
		String[][] arr=null;
		try {
			sql="SELECT nation,count(*) FROM insta WHERE nation=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, var);
			rs=ps.executeQuery();
			arr=makeArray05(rs,var2);
			System.out.println(arr);
			return arr;
		}finally { DBCon.close(ps);}
	}
	
	public String[][] stat3(String var,String var2,String var4) throws SQLException{
		String sql="";
		String var3="gender";
		String[][] arr=null;
		try {
			sql="SELECT gender,count(*) FROM insta WHERE birth>=? AND birth<? AND gender=?";
			ps=con.prepareStatement(sql);
			ps.setString(1,var);
			ps.setString(2,var2);
			ps.setString(3, var4);
			rs=ps.executeQuery();
			arr=makeArray05(rs,var3);
			return arr;
		} finally {
			DBCon.close(ps);
		}
	}
	
	public String[][] stat4(String var,String var2,String var4) throws SQLException{
		String sql="";
		String var3="nation";
		String[][] arr=null;
		try {
			sql="SELECT nation,count(*) FROM insta WHERE birth>=? AND birth<? AND nation=?";
			ps=con.prepareStatement(sql);
			ps.setString(1,var);
			ps.setString(2,var2);
			ps.setString(3, var4 );
			rs=ps.executeQuery();
			arr=makeArray05(rs,var3);
			return arr;
		} finally {
			DBCon.close(ps);
		}
	}
	
    public int resetNums() {
        try {
           String sql1 = "ALTER TABLE insta AUTO_INCREMENT=1";
           ps=con.prepareStatement(sql1);
           ps.executeUpdate();
           
           String sql2 = "SET @COUNT = 0";
           ps=con.prepareStatement(sql2);
           ps.executeUpdate();
           
           String sql3 = "UPDATE insta SET no = @COUNT:=@COUNT+1";
           ps=con.prepareStatement(sql3);   
           return ps.executeUpdate();
           
        } catch (Exception e) {
           e.printStackTrace();
           return -1;
        }finally {
           DBCon.close(ps);
        }
     }
	public void close() {
		try {
			if (con!=null)
				con.close();
		} catch (SQLException e) {}
	}
	

}