package org.nearbyshops.DAOs.DAORoles;


import org.nearbyshops.Constants;
import org.nearbyshops.Model.ModelEndpoint.UserEndpoint;
import org.nearbyshops.Model.ModelRoles.*;
import org.nearbyshops.Utility.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sumeet on 21/4/17.
 */


@Component
public class DAOUserNew {

    @Autowired
    DataSource dataSource;



    public int updatePassword(User user, String oldPassword)
    {

//        + User.USERNAME + " = ?"




//        Gson gson = new Gson();
//        System.out.println(gson.toJson(user) + "\nOldPassword : " + oldPassword);


        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "
                + User.PASSWORD + "=?"

                + " WHERE "
                + " ( " + User.USERNAME + " = ? "
                + " OR " +  User.USER_ID + " = ? "
                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + ")"
                + ")"
                + " AND " + User.PASSWORD + " = ?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

//            statement.setString(++i,user.getToken());
//            statement.setTimestamp(++i,user.getTimestampTokenExpires());

            statement.setString(++i,user.getPassword());


            statement.setString(++i,user.getUsername());
            statement.setObject(++i,user.getUserID());
            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getPhone());

            statement.setString(++i,oldPassword);


            rowCountUpdated = statement.executeUpdate();

            System.out.println("Total rows updated: " + rowCountUpdated);


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return rowCountUpdated;
    }



    public int updateEmail(User user)
    {

        Connection connection = null;
        PreparedStatement statement = null;

//        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertItemSubmission = " UPDATE " + User.TABLE_NAME
                                    + " SET "    + User.E_MAIL + " = " + EmailVerificationCode.TABLE_NAME + "." + EmailVerificationCode.EMAIL
                                    + " FROM "   + EmailVerificationCode.TABLE_NAME
                                    + " WHERE "  + EmailVerificationCode.TABLE_NAME + "." + EmailVerificationCode.EMAIL + " = ? "
                                    + " AND "    + EmailVerificationCode.TABLE_NAME + "." + EmailVerificationCode.VERIFICATION_CODE + " = ?"
                                    + " AND "    + EmailVerificationCode.TABLE_NAME + "." + EmailVerificationCode.TIMESTAMP_EXPIRES + " > now()"
                                    + " AND "    + User.TABLE_NAME + "." + User.USER_ID + " = ?";

//        + " AND "    + User.TABLE_NAME + "." + User.PASSWORD + " = ?"

        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;


            // check email is verification code to ensure email belongs to user
            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getRt_email_verification_code());
            statement.setObject(++i,user.getUserID());
//            statement.setString(++i,user.getPassword());


            rowCountItems = statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

//                    idOfInsertedRow=-1;
                    rowCountItems = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally
        {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }



        return rowCountItems;
    }



    public int updatePhone(User user)
    {

        Connection connection = null;
        PreparedStatement statement = null;

//        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertItemSubmission = " UPDATE " + User.TABLE_NAME
                + " SET "    + User.PHONE + " = " + PhoneVerificationCode.TABLE_NAME + "." + PhoneVerificationCode.PHONE
                + " FROM "   + PhoneVerificationCode.TABLE_NAME
                + " WHERE "  + PhoneVerificationCode.TABLE_NAME + "." + PhoneVerificationCode.PHONE + " = ? "
                + " AND "    + PhoneVerificationCode.TABLE_NAME + "." + PhoneVerificationCode.VERIFICATION_CODE + " = ?"
                + " AND "    + PhoneVerificationCode.TABLE_NAME + "." + PhoneVerificationCode.TIMESTAMP_EXPIRES + " > now()"
                + " AND "    + User.TABLE_NAME + "." + User.USER_ID + " = ?";


//        + " AND "    + User.TABLE_NAME + "." + User.PASSWORD + " = ?"

        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;





            // check email is verification code to ensure email belongs to user
            statement.setString(++i,user.getPhoneWithCountryCode());
            statement.setString(++i,user.getRt_phone_verification_code());
            statement.setObject(++i,user.getUserID());

//            statement.setString(++i,user.getPassword());


            rowCountItems = statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

//                    idOfInsertedRow=-1;
                    rowCountItems = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally
        {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }



        return rowCountItems;
    }





    public User checkGoogleID(String googleID) {


        String query = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
                + User.PASSWORD + ","
                + User.E_MAIL + ","
                + User.PHONE + ","
                + User.NAME + ","
                + User.GENDER + ","
//                + User.PROFILE_IMAGE_ID + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ""

                + " FROM " + User.TABLE_NAME;




        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        System.out.println("Checked Google ID : " + googleID);


        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            statement.setObject(1,googleID);

            rs = statement.executeQuery();


            while(rs.next())
            {
                user = new User();

                user.setUserID(rs.getInt(User.USER_ID));
                user.setUsername(rs.getString(User.USERNAME));
                user.setPassword(rs.getString(User.PASSWORD));
                user.setEmail(rs.getString(User.E_MAIL));
                user.setPhone(rs.getString(User.PHONE));
                user.setName(rs.getString(User.NAME));
                user.setGender(rs.getBoolean(User.GENDER));
//                user.setProfileImageID(rs.getInt(User.PROFILE_IMAGE_ID));
                user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                user.setAbout(rs.getString(User.ABOUT));
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return user;
    }




    public int saveGoogleProfile(User user, boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statement = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;

        String insertItemSubmission = "INSERT INTO "
                + User.TABLE_NAME
                + "("

                + User.USERNAME + ","
                + User.PASSWORD + ","
                + User.E_MAIL + ","

                + User.PHONE + ","
                + User.NAME + ","
                + User.GENDER + ","

//                + User.PROFILE_IMAGE_ID + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","

                + User.TOKEN + ","
                + User.TIMESTAMP_TOKEN_EXPIRES + ","

                + User.ABOUT + ""


                + ") VALUES(?,?,? ,?,?,? ,?,?,?  ,?,?, ?)";


        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statement = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statement.setString(++i,user.getUsername());
            statement.setString(++i,user.getPassword());
            statement.setString(++i,user.getEmail());

            statement.setString(++i,user.getPhone());
            statement.setString(++i,user.getName());
            statement.setObject(++i,user.getGender());

//            statement.setInt(++i,user.getProfileImageID());
            statement.setObject(++i,user.getRole());
            statement.setObject(++i,user.isAccountPrivate());

            statement.setString(++i,user.getToken());
            statement.setTimestamp(++i,user.getTimestampTokenExpires());

            statement.setString(++i,user.getAbout());

            rowCountItems = statement.executeUpdate();


            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }



            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {

                    idOfInsertedRow=-1;
                    rowCountItems = 0;

                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally
        {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(getRowCount)
        {
            return rowCountItems;
        }
        else
        {
            return idOfInsertedRow;
        }
    }




    public int deleteUser(int userID)
    {

        String deleteStatement = "DELETE FROM " + User.TABLE_NAME
                            + " WHERE " + User.USER_ID + " = " + userID;



        Connection connection= null;
        Statement statement = null;
        int rowsCountDeleted = 0;
        try {

            connection = dataSource.getConnection();
            statement = connection.createStatement();

            rowsCountDeleted = statement.executeUpdate(deleteStatement);

            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return rowsCountDeleted;
    }





    public int updateUser(User user)
    {

        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET "

//                + User.USERNAME + "=?,"
//                + User.PASSWORD + "=?,"
//                + User.E_MAIL + "=?,"
//                + User.PHONE + "=?,"
                + User.NAME + "=?,"
                + User.SECRET_CODE + "=?,"
                + User.GENDER + "=?,"

                + User.PROFILE_IMAGE_URL + "=?,"
                + User.IS_ACCOUNT_PRIVATE + "=?,"
                + User.ABOUT + "=?"

                + " WHERE " + User.USER_ID + " = ?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

//            statement.setString(++i,user.getUsername());
//            statement.setString(++i,user.getPassword());
//            statement.setString(++i,user.getEmail());
//            statement.setString(++i,user.getPhone());

            statement.setString(++i,user.getName());
            statement.setInt(++i,user.getSecretCode());
            statement.setObject(++i,user.getGender());

            statement.setString(++i,user.getProfileImagePath());
            statement.setObject(++i,user.isAccountPrivate());
            statement.setString(++i,user.getAbout());

            statement.setObject(++i,user.getUserID());


            rowCountUpdated = statement.executeUpdate();


            System.out.println("Total rows updated: " + rowCountUpdated);


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return rowCountUpdated;
    }



    public int updateUserByAdmin(User user)
    {

        String updateStatement = "UPDATE " + User.TABLE_NAME
                                + " SET " + User.USERNAME + "=?,"
                                        + User.ROLE + "=?,"
                                        + User.E_MAIL + "=?,"
                                        + User.PHONE + "=?,"

                                        + User.NAME + "=?,"
                                        + User.GENDER + "=?,"

                                        + User.PROFILE_IMAGE_URL + "=?,"
                                        + User.IS_ACCOUNT_PRIVATE + "=?,"
                                        + User.ABOUT + "=?"

                                + " WHERE " + User.USER_ID + " = ?";





        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);



            if(user.getEmail()!=null && user.getEmail().equals(""))
            {
                user.setEmail(null);
            }

            if(user.getPhone()!=null && user.getPhone().equals(""))
            {
                user.setPhone(null);
            }

            if(user.getUsername()!=null && user.getUsername().equals(""))
            {
                user.setUsername(null);
            }



            int i = 0;

            statement.setString(++i,user.getUsername());
            statement.setInt(++i,user.getRole());
            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getPhone());

            statement.setString(++i,user.getName());
            statement.setObject(++i,user.getGender());

            statement.setString(++i,user.getProfileImagePath());
            statement.setObject(++i,user.isAccountPrivate());
            statement.setString(++i,user.getAbout());

            statement.setObject(++i,user.getUserID());


            rowCountUpdated = statement.executeUpdate();


//            System.out.println("Total rows updated: " + rowCountUpdated);


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return rowCountUpdated;
    }




    public int updateToken(User user)
    {


        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET " + User.TOKEN + " = ? "
                + " WHERE "
                + " ( " + User.USERNAME + " = ? "
                + " OR " +  User.USER_ID + " = ? "
                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + ")"
                + ")"
                + " AND " + User.PASSWORD + " = ? ";

//        + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? "
//                + User.USERNAME + " = ?"
//                + " AND " + User.PASSWORD + " = ?";



//
//        String insertToken = "";
//
//        insertToken = "INSERT INTO "
//                + UserTokens.TABLE_NAME
//                + "("
//                + UserTokens.LOCAL_USER_ID + ","
//                + UserTokens.TOKEN_STRING + ""
//                + ") SELECT "
//                + User.USER_ID + ","
//                + " ? " + ""
//                + " FROM "  + User.TABLE_NAME
//                + " WHERE "
//                + " ( " + User.USERNAME + " = ? "
//                + " OR " +  User.USER_ID + " = ? "
//                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
//                + " OR " + " ( " + User.PHONE + " = ?" + ")"
//                + ")";






        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);



            String generatedToken = new BigInteger(130, Globals.random).toString(32);
            user.setToken(generatedToken);


            int i = 0;

            statement.setString(++i,user.getToken());
//            statement.setTimestamp(++i,user.getTimestampTokenExpires());

//            statement.setString(++i,username); // username
//            statement.setString(++i,username); // userID
//            statement.setString(++i,username); // email
//            statement.setString(++i,username); // phone
//            statement.setString(++i,token); // token

            statement.setString(++i,user.getUsername());
            statement.setObject(++i,user.getUserID());
            statement.setString(++i,user.getEmail());
            statement.setString(++i,user.getPhone());

            statement.setString(++i,user.getPassword());

            rowCountUpdated = statement.executeUpdate();



//
//            statement = connection.prepareStatement(insertToken);
//
//            i = 0;
//
//            statement.setString(++i,user.getToken());
//            statement.setString(++i,user.getUsername());
//            statement.setObject(++i,user.getUserID());
//            statement.setString(++i,user.getEmail());
//            statement.setString(++i,user.getPhone());
//
//            statement.executeUpdate();


//            System.out.println("Total rows updated: " + rowCountUpdated);


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return rowCountUpdated;
    }


    public int updateToken(String username)
    {


        String updateStatement = "UPDATE " + User.TABLE_NAME

                + " SET " + User.TOKEN + " = ? "
                + " WHERE "
                + " ( " + User.USERNAME + " = ? "
                + " OR " + " CAST ( " +  User.USER_ID + " AS text )  = ? "
                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + ")"
                + ")";

//        +  User.USER_ID + " = ? "

//        + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? "
//                + User.USERNAME + " = ?"
//                + " AND " + User.PASSWORD + " = ?";



//
//        String insertToken = "";
//
//        insertToken = "INSERT INTO "
//                + UserTokens.TABLE_NAME
//                + "("
//                + UserTokens.LOCAL_USER_ID + ","
//                + UserTokens.TOKEN_STRING + ""
//                + ") SELECT "
//                + User.USER_ID + ","
//                + " ? " + ""
//                + " FROM "  + User.TABLE_NAME
//                + " WHERE "
//                + " ( " + User.USERNAME + " = ? "
//                + " OR " +  User.USER_ID + " = ? "
//                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
//                + " OR " + " ( " + User.PHONE + " = ?" + ")"
//                + ")";






        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);



            String generatedToken = new BigInteger(130, Globals.random).toString(32);


            int i = 0;

            statement.setString(++i,generatedToken);
//            statement.setTimestamp(++i,user.getTimestampTokenExpires());

            statement.setString(++i,username); // username
            statement.setString(++i,username); // userID
            statement.setString(++i,username); // email
            statement.setString(++i,username); // phone
//            statement.setString(++i,token); // token

            rowCountUpdated = statement.executeUpdate();



//
//            statement = connection.prepareStatement(insertToken);
//
//            i = 0;
//
//            statement.setString(++i,user.getToken());
//            statement.setString(++i,user.getUsername());
//            statement.setObject(++i,user.getUserID());
//            statement.setString(++i,user.getEmail());
//            statement.setString(++i,user.getPhone());
//
//            statement.executeUpdate();


//            System.out.println("Total rows updated: " + rowCountUpdated);


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return rowCountUpdated;
    }





    // fetch profile details by the user for his own profile
    public User getProfile(int userID)
    {

        boolean isFirst = true;

        String query = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
                + User.E_MAIL + ","
                + User.PHONE + ","
                + User.NAME + ","
                + User.SECRET_CODE + ","
                + User.GENDER + ","
                + User.PROFILE_IMAGE_URL + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE " + User.USER_ID  + " = ? ";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;


            statement.setObject(++i,userID); // username


            rs = statement.executeQuery();

            while(rs.next())
            {
                user = new User();

                user.setUserID(rs.getInt(User.USER_ID));
                user.setUsername(rs.getString(User.USERNAME));
                user.setEmail(rs.getString(User.E_MAIL));
                user.setPhone(rs.getString(User.PHONE));
                user.setName(rs.getString(User.NAME));
                user.setSecretCode(rs.getInt(User.SECRET_CODE));
                user.setGender(rs.getBoolean(User.GENDER));
                user.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
                user.setRole(rs.getInt(User.ROLE));
                user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                user.setAbout(rs.getString(User.ABOUT));
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return user;
    }




    public User checkTokenAndGetProfile(String username)
    {
        User user = getProfileWithToken(username);

        if(user==null)
        {
            return null;
        }

        if(user.getToken()==null || user.getToken().equals(""))
        {
            updateToken(username);
            user = getProfileWithToken(username);
        }

        return user;
    }




    public User getProfileWithToken(String username)
    {


        String query = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
//                + User.PASSWORD + ","
                + User.E_MAIL + ","
                + User.PHONE + ","
//                + User.IS_PHONE_VERIFIED + ","
                + User.NAME + ","
                + User.SECRET_CODE + ","
                + User.GENDER + ","
                + User.PROFILE_IMAGE_URL + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ","
                + User.SERVICE_ACCOUNT_BALANCE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.TOKEN + ""
//                + User.TIMESTAMP_TOKEN_EXPIRES + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE "
                + " ( " + User.USERNAME + " = ? "
                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? "
                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + ")"
                + ")";



//                + " ( " + User.USERNAME + " = ? "
//                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? )";

//        CAST (" + User.TIMESTAMP_TOKEN_EXPIRES + " AS TIMESTAMP)"



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;


            statement.setString(++i,username); // username
            statement.setString(++i,username); // userID
            statement.setString(++i,username); // email
            statement.setString(++i,username); // phone


//            statement.setString(++i,username); // username
//            statement.setString(++i,username); // userID


            rs = statement.executeQuery();

            while(rs.next())
            {
                user = new User();

                user.setUserID(rs.getInt(User.USER_ID));
                user.setUsername(rs.getString(User.USERNAME));
//                user.setPassword(rs.getString(User.PASSWORD));
                user.setEmail(rs.getString(User.E_MAIL));
                user.setPhone(rs.getString(User.PHONE));
//                user.setPhoneVerified(rs.getBoolean(User.IS_PHONE_VERIFIED));
                user.setName(rs.getString(User.NAME));
                user.setSecretCode(rs.getInt(User.SECRET_CODE));
                user.setGender(rs.getBoolean(User.GENDER));
                user.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
                user.setRole(rs.getInt(User.ROLE));
                user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                user.setServiceAccountBalance(rs.getDouble(User.SERVICE_ACCOUNT_BALANCE));
                user.setAbout(rs.getString(User.ABOUT));

                user.setToken(rs.getString(User.TOKEN));
//                user.setTimestampTokenExpires(rs.getTimestamp(User.TIMESTAMP_TOKEN_EXPIRES));

//
//                if(user.getToken()==null || user.getToken().equals(""))
//                {
//                    updateToken(user);
//                }

            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return user;
    }




    public User getProfileUsingToken(String username, String token)
    {

        boolean isFirst = true;

        String query = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
//                + User.PASSWORD + ","
                + User.E_MAIL + ","
                + User.PHONE + ","
//                + User.IS_PHONE_VERIFIED + ","
                + User.NAME + ","
                + User.SECRET_CODE + ","
                + User.GENDER + ","
                + User.PROFILE_IMAGE_URL + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ","
                + User.SERVICE_ACCOUNT_BALANCE + ","
                + User.IS_ACCOUNT_PRIVATE + ""
//                + User.TOKEN + ","
//                + User.TIMESTAMP_TOKEN_EXPIRES + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE "
                + " ( " + User.USERNAME + " = ? "
                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? "
                + " OR " + " ( " + User.E_MAIL + " = ?" + ")"
                + " OR " + " ( " + User.PHONE + " = ?" + ")"
                + ")"
                + " AND " + User.TOKEN + " = ? ";



//                + " ( " + User.USERNAME + " = ? "
//                + " OR " + " CAST ( " +  User.USER_ID + " AS text ) " + " = ? )";

//        CAST (" + User.TIMESTAMP_TOKEN_EXPIRES + " AS TIMESTAMP)"



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;


            statement.setString(++i,username); // username
            statement.setString(++i,username); // userID
            statement.setString(++i,username); // email
            statement.setString(++i,username); // phone
            statement.setString(++i,token); // password


//            statement.setString(++i,username); // username
//            statement.setString(++i,username); // userID


            rs = statement.executeQuery();

            while(rs.next())
            {
                user = new User();

                user.setUserID(rs.getInt(User.USER_ID));
                user.setUsername(rs.getString(User.USERNAME));
//                user.setPassword(rs.getString(User.PASSWORD));
                user.setEmail(rs.getString(User.E_MAIL));
                user.setPhone(rs.getString(User.PHONE));
//                user.setPhoneVerified(rs.getBoolean(User.IS_PHONE_VERIFIED));
                user.setName(rs.getString(User.NAME));
                user.setSecretCode(rs.getInt(User.SECRET_CODE));
                user.setGender(rs.getBoolean(User.GENDER));
                user.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
                user.setRole(rs.getInt(User.ROLE));
                user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                user.setServiceAccountBalance(rs.getDouble(User.SERVICE_ACCOUNT_BALANCE));
                user.setAbout(rs.getString(User.ABOUT));

//                user.setToken(rs.getString(User.TOKEN));
//                user.setTimestampTokenExpires(rs.getTimestamp(User.TIMESTAMP_TOKEN_EXPIRES));

            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return user;
    }




    public UserEndpoint getUsers(
            Integer userRole,
            Integer shopID,
            Boolean gender,
            String searchString,
            String sortBy,
            int limit, int offset,
            boolean getRowCount,
            boolean getOnlyMetadata
    ) {


        String queryCount = "";

        String queryJoin = "SELECT DISTINCT "

                + User.TABLE_NAME + "." + User.USER_ID + ","
                + User.TABLE_NAME + "." + User.NAME + ","
                + User.TABLE_NAME + "." + User.ROLE + ","
                + User.TABLE_NAME + "." + User.PROFILE_IMAGE_URL + ","
                + User.TABLE_NAME + "." + User.TIMESTAMP_CREATED + ","
                + User.TABLE_NAME + "." + User.IS_VERIFIED + ""

                + " FROM " + User.TABLE_NAME
                + " LEFT OUTER JOIN " + StaffPermissions.TABLE_NAME + " ON (" + StaffPermissions.TABLE_NAME + "." + StaffPermissions.STAFF_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " LEFT OUTER JOIN " + ShopStaffPermissions.TABLE_NAME + " ON (" + ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.STAFF_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " LEFT OUTER JOIN " + DeliveryGuyData.TABLE_NAME + " ON (" + DeliveryGuyData.TABLE_NAME + "." + DeliveryGuyData.STAFF_USER_ID + " = " + User.TABLE_NAME + "." + User.USER_ID + ")"
                + " WHERE TRUE ";


//        " AND " + User.TABLE_NAME + "." + User.ROLE + " = " + GlobalConstants.ROLE_STAFF_CODE




        if(userRole != null)
        {
            queryJoin = queryJoin + " AND " + User.TABLE_NAME + "." + User.ROLE + " = ?";
        }



        if(gender != null)
        {
            queryJoin = queryJoin + " AND " + User.TABLE_NAME + "." + User.GENDER + " = ?";
        }



        if(shopID!=null)
        {

            if(userRole==null || userRole== Constants.ROLE_DELIVERY_GUY_SELF_CODE)
            {
                queryJoin = queryJoin + " AND ( " + DeliveryGuyData.TABLE_NAME + "." + DeliveryGuyData.SHOP_ID + " = ? )";
            }
            else
            {
                queryJoin = queryJoin + " AND ( " + ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.SHOP_ID + " = ? )";
            }
        }




        if(searchString !=null)
        {
            String queryPartSearch = User.TABLE_NAME + "." + User.NAME +" ilike '%" + searchString + "%'"
                    + " or CAST ( " + User.TABLE_NAME + "." + User.USER_ID + " AS text )" + " ilike '%" + searchString + "%'" + " ";

            queryJoin = queryJoin + " AND " + queryPartSearch;
        }






        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + DeliveryGuyData.TABLE_NAME + "." + DeliveryGuyData.DATA_ID + ","
                + ShopStaffPermissions.TABLE_NAME + "." + ShopStaffPermissions.PERMISSION_ID + ","
                + StaffPermissions.TABLE_NAME + "." + StaffPermissions.PERMISSION_ID + ","
                + User.TABLE_NAME + "." + User.USER_ID;




        queryCount = queryJoin;



        if(sortBy!=null && !sortBy.equals(""))
        {
            String queryPartSortBy = " ORDER BY " + sortBy;
            queryJoin = queryJoin + queryPartSortBy;
        }





        queryJoin = queryJoin + " LIMIT " + limit + " " + " OFFSET " + offset;





		/*

		Applying filters Ends

		 */

        // Applying filters




        queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";


        UserEndpoint endPoint = new UserEndpoint();
        ArrayList<User> itemList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

//        PreparedStatement statementCount = null;
//        ResultSet resultSetCount = null;

        try {

            connection = dataSource.getConnection();

            int i = 0;


            if(!getOnlyMetadata)
            {
                statement = connection.prepareStatement(queryJoin);


                if(userRole!=null)
                {
                    statement.setObject(++i,userRole);
                }

                if(gender!=null)
                {
                    statement.setObject(++i,gender);
                }


                if(shopID!=null)
                {
                    statement.setObject(++i,shopID);
//                    statement.setObject(++i,shopID);
                }


                rs = statement.executeQuery();

                while(rs.next())
                {

                    User user = new User();

                    user.setUserID(rs.getInt(User.USER_ID));
                    user.setName(rs.getString(User.NAME));
                    user.setRole(rs.getInt(User.ROLE));
                    user.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
                    user.setTimestampCreated(rs.getTimestamp(User.TIMESTAMP_CREATED));
                    user.setVerified(rs.getBoolean(User.IS_VERIFIED));

                    itemList.add(user);
                }

                endPoint.setResults(itemList);

            }


            if(getRowCount)
            {
                statement = connection.prepareStatement(queryCount);

                i = 0;


                if(userRole!=null)
                {
                    statement.setObject(++i,userRole);
                }


                if(gender!=null)
                {
                    statement.setObject(++i,gender);
                }




                if(shopID!=null)
                {
                    statement.setObject(++i,shopID);
//                    statement.setObject(++i,shopID);
                }




                rs = statement.executeQuery();

                while(rs.next())
                {
                    endPoint.setItemCount(rs.getInt("item_count"));
                }
            }






        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return endPoint;
    }





    // fetch user details for admin and staff
    public User getUserDetails(int userID)
    {

        String query = "SELECT "

                + User.USER_ID + ","
                + User.USERNAME + ","
                + User.E_MAIL + ","
                + User.PHONE + ","
                + User.NAME + ","
                + User.SECRET_CODE + ","
                + User.GENDER + ","
                + User.PROFILE_IMAGE_URL + ","
                + User.ROLE + ","
                + User.IS_ACCOUNT_PRIVATE + ","
                + User.ABOUT + ""

                + " FROM " + User.TABLE_NAME
                + " WHERE " + User.USER_ID  + " = ? ";



        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;


        //Distributor distributor = null;
        User user = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);

            int i = 0;


            statement.setObject(++i,userID); // username


            rs = statement.executeQuery();

            while(rs.next())
            {
                user = new User();

                user.setUserID(rs.getInt(User.USER_ID));
                user.setUsername(rs.getString(User.USERNAME));
                user.setEmail(rs.getString(User.E_MAIL));
                user.setPhone(rs.getString(User.PHONE));
                user.setName(rs.getString(User.NAME));
                user.setSecretCode(rs.getInt(User.SECRET_CODE));
                user.setGender(rs.getBoolean(User.GENDER));
                user.setProfileImagePath(rs.getString(User.PROFILE_IMAGE_URL));
                user.setRole(rs.getInt(User.ROLE));
                user.setAccountPrivate(rs.getBoolean(User.IS_ACCOUNT_PRIVATE));
                user.setAbout(rs.getString(User.ABOUT));
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return user;
    }


}


