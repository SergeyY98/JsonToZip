package com.example.task.dbconnector;

import com.example.task.domain.AgencyField;
import com.example.task.domain.AgencyScope;
import com.example.task.domain.AgencyScopesAndOkved;
import com.example.task.domain.Content;
import com.example.task.domain.Email;
import com.example.task.domain.Phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Date;

public class PostgreSQLJDBC {
  public void init(Content[] content, long lastUpdateFrom, long lastUpdateTo) {
    try {
      Class.forName("org.postgresql.Driver");
      Connection c = DriverManager
          .getConnection("jdbc:postgresql://localhost:5432/db",
              "postgres", "postgres");
      System.out.println("Opened database successfully");

      Statement stmt = c.createStatement();
      String sql = "CREATE TABLE IF NOT EXISTS CONTENT" +
          "(id TEXT, " +
          " create_datetime TEXT, " +
          " auth_fullname   TEXT, " +
          " auth_shortname  TEXT, " +
          " auth_agency_inn TEXT, " +
          " auth_agency_kpp TEXT, " +
          " auth_sr_code    TEXT, " +
          " auth_gmu_code   TEXT, " +
          " fullname        TEXT, " +
          " shortname       TEXT, " +
          " agency_inn      TEXT, " +
          " agency_kpp      TEXT, " +
          " sr_code         TEXT, " +
          " gmu_code        TEXT, " +
          " ppo_code        TEXT, " +
          " ppo_name        TEXT, " +
          " opf_code        TEXT, " +
          " opf_name        TEXT, " +
          " scope_code      TEXT, " +
          " scope_name      TEXT, " +
          " zip             TEXT, " +
          " rajon_name      TEXT, " +
          " street_name     TEXT, " +
          " building        TEXT, " +
          " room            TEXT, " +
          " loc_oktmo_code  TEXT, " +
          " loc_oktmo_name  TEXT, " +
          " ag_oktmo_code   TEXT, " +
          " ag_oktmo_name   TEXT, " +
          " reg_date        DATE, " +
          " subject_code    TEXT, " +
          " subject_name    TEXT, " +
          " email           TEXT, " +
          " phone           TEXT, " +
          " scopes_code     TEXT, " +
          " scopes_name     TEXT, " +
          " ac_type_code    TEXT, " +
          " ac_type_name    TEXT, " +
          " oktmo_code      TEXT, " +
          " oktmo_name      TEXT, " +
          " lst_update_from DATE, " +
          " lst_update_to   DATE)";
      stmt.executeUpdate(sql);
      stmt.close();
      addContent(c, content, lastUpdateFrom, lastUpdateTo);
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName()+": "+ e.getMessage() );
      System.exit(0);
    }
    System.out.println("Table created successfully");
  }

  public static void addContent(Connection connection, Content[] content,
                                long lastUpdateFrom, long lastUpdateTo) throws Exception {
    String insertQuery = "INSERT INTO content(id, create_datetime, auth_fullname, auth_shortname, auth_agency_inn, " +
        "auth_agency_kpp, auth_sr_code, auth_gmu_code, fullname, shortname, agency_inn, agency_kpp, sr_code, gmu_code, " +
        "ppo_code, ppo_name, opf_code, opf_name, scope_code, scope_name, reg_date, subject_code, subject_name, " +
        "email, phone, scopes_code, scopes_name, ac_type_code, ac_type_name, oktmo_code, oktmo_name, lst_update_from, lst_update_to) " +
        "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        //"ON DUPLICATE KEY UPDATE lst_update_from = ? AND lst_update_to = ?";
    PreparedStatement statement = connection.prepareStatement(insertQuery);
    for (Content value : content) {
      for (AgencyField agency : value.getAuthorizedAgencies()) {
        for (AgencyScope agencyScope : agency.getAgency().getAgencyScopes()) {
          for (Email email : agency.getAuthAgencyEmails()) {
            for (Phone phone : agency.getAuthAgencyPhones()) {
              for (AgencyScopesAndOkved scopes : agency.getAgencyScopesAndOkvedList()) {
                statement.setString(1, value.getId());
                statement.setDate(2, new Date(value.getCreateDateTime().getTime()));
                statement.setString(3, value.getAuthorAgency().getFullName());
                statement.setString(4, value.getAuthorAgency().getShortName());
                statement.setString(5, value.getAuthorAgency().getAgencyInn());
                statement.setString(6, value.getAuthorAgency().getAgencyKpp());
                statement.setString(7, value.getAuthorAgency().getSrCode());
                statement.setString(8, value.getAuthorAgency().getGmuCode());
                statement.setString(9, agency.getAgency().getFullName());
                statement.setString(10, agency.getAgency().getShortName());
                statement.setString(11, agency.getAgency().getAgencyInn());
                statement.setString(12, agency.getAgency().getAgencyKpp());
                statement.setString(13, agency.getAgency().getSrCode());
                statement.setString(14, agency.getAgency().getGmuCode());
                statement.setString(15, agency.getAgency().getAgencyPpo().getPpoCode());
                statement.setString(16, agency.getAgency().getAgencyPpo().getPpoName());
                statement.setString(17, agency.getAgency().getAgencyOpf().getOpfCode());
                statement.setString(18, agency.getAgency().getAgencyOpf().getOpfName());
                statement.setString(19, agencyScope.getScopeCode());
                statement.setString(20, agencyScope.getScopeName());
                statement.setDate(21, new Date(agency.getRegDate().getTime()));
                statement.setString(22, agency.getAgencyAddress().getAgencySubject().getSubjectCode());
                statement.setString(23, agency.getAgencyAddress().getAgencySubject().getSubjectName());
                statement.setString(24, email.getEmail());
                statement.setString(25, phone.getPhone().getPhoneNum());
                statement.setString(26, scopes.getAgencyScopesCode());
                statement.setString(27, scopes.getAgencyScopesName());
                statement.setString(28, scopes.getEconomicActivityTypeCode());
                statement.setString(29, scopes.getEconomicActivityTypeName());
                statement.setString(30, agency.getAgencyOktmo().getOktmoCode());
                statement.setString(31, agency.getAgencyOktmo().getOktmoName());
                statement.setDate(32, new Date(lastUpdateFrom));
                statement.setDate(33, new Date(lastUpdateTo));
                //statement.setDate(34, new Date(lastUpdateFrom));
                //statement.setDate(35, new Date(lastUpdateTo));
                statement.addBatch();
              }
            }
          }
        }
      }
    }
    statement.executeBatch();
    statement.close();
  }
}