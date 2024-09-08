package test;

import dev.motomoto.bunnyAPI.BunnyAPI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JavaAPItest {
    public static void main(String[] args) throws SQLException {
        
        //Beispiel: Speichern eines Wertes in der Datanbank
        BunnyAPI.Companion.getInstance().getDataHandleSimpleArgs().setArgValue("ordnerName", "tischName", "schlüsselName", "Das ist der gespeicherte Wert.");
        
        //Beispiel: Wie leicht man mehrere Wert in der Datenbank speichern kann mit nur einer Methode
        List <String> values = List.of("Wert1", "Wert2", "Wert3", "Wert4");
        BunnyAPI.Companion.getInstance().getDataHandleSimpleArgs().setArgsValues("ordnerName1", "tischName1", "schlüsselName", values);
        
        //So könnte man einenn Eintrag nachträglich ändern! In dem Beispiel schreibe ich den Wert2 um auf "Das ist der gespeicherte Wert." bei index gibt man an welcher Wert in der Liste geändert werden soll.
        BunnyAPI.Companion.getInstance().getDataHandleSimpleArgs().replaceArgValue("ordnerName1", "tischName1", "schlüsselName", 1, "Das ist der gespeicherte Wert.");
        
        //In dem Beispiel lasse ich mir den Wert den ich umgeschrieben habe in dem oberen Beispiel ausgeben. In die Console! (So kann man einen bestimmten index abfragen!)
       System.out.println(BunnyAPI.Companion.getInstance().getDataHandleSimpleArgs().readArgs("ordnerName1", "tischName1", "schlüsselName", 1));
       
       //Ausgabe wie viele Keys in einem Table stehen! z.b.: für schleifen.
       System.out.println(BunnyAPI.Companion.getInstance().getDataHandleSimpleArgs().readTableCountKeys("ordnerName1", "tischName1"));
       
       //Damit kannst dir alle Keys in einem Table ausgeben lassen! z.b.: um abzufragen ob eine USER ID schon vorhanden ist!
       System.out.println(BunnyAPI.Companion.getInstance().getDataHandleSimpleArgs().readTableKeys("ordnerName1", "tischName1"));
       
       //In dem Speicher ich einen Text mit zeichen damit diese unten mit readMessageWithPlaceholders() ausgegeben werden können. (Du kannst so viel replacen wie du möchtest und was du möchtest!
       BunnyAPI.Companion.getInstance().getDataHandleSimpleArgs().setArgValue("ordnerName", "tischName2", "schlüsselName", "Der Spieler $ hatte heute % Punkte gewonnen. Sie wurden bezahlt von &");
       System.out.println(BunnyAPI.Companion.getInstance().getDataHandleSimpleArgs().readMessageWithPlaceholders("ordnerName", "tischName2", "schlüsselName", 0, "$", "Tommy", "%", "100.00.000", "&", "Admin"));
       
       //In dem Speicher ich für die Lobby einen Prefix diesen können die Admins in der Datei immer ändern und frage diesen einfach ab. readColorCodes() kann & zu § umschreiben!
       BunnyAPI.Companion.getInstance().getDataHandleSimpleArgs().setArgValue("Lobby", "Messages", "prefix", "&8[&6Lobby&8] &7");
       System.out.println(BunnyAPI.Companion.getInstance().getDataHandleSimpleArgs().readColorCodes("Lobby", "Messages", "prefix", 0));
       
       
       //======================================================================================================================================================================================
       
       
       //In dem Beispiel lösche ich einen Ordner wieder
       BunnyAPI.Companion.getInstance().getDataHandleSimpleDelete().deleteFolder("Lobby");
       
       //In dem Beispiel lösche ich einen Key wieder aus einem Table!
       BunnyAPI.Companion.getInstance().getDataHandleSimpleDelete().deleteKey("ordnerName1", "tischName1", "schlüsselName");
       
       //In dem Beispiel lösche ich einen Table wieder!
       BunnyAPI.Companion.getInstance().getDataHandleSimpleDelete().deleteTable("ordnerName1", "tischName1");
       
       //In dem Beispiel lösche ich einen Wert aus einem Key! Falls mehrere Werte in einem Key stehen werden die anderen stehen bleiben!
       BunnyAPI.Companion.getInstance().getDataHandleSimpleDelete().deleteArgValue("Lobby", "Message", "prefix", 0);
        
       
        //======================================================================================================================================================================================
        
        
        //Mit der Funktion kann man schauen ob ein Ordner existiert! NullCheck wird immer einen Boolischen Wert zurückgeben! Sprich false oder true!
       BunnyAPI.Companion.getInstance().getDataHandleSimpleNullCheck().isFolderExists("Lobby");
       //So könnte das aussehen!
       if (BunnyAPI.Companion.getInstance().getDataHandleSimpleNullCheck().isFolderExists("Lobby")) {
           System.out.println("Der Ordner existiert!");
       } else {
           System.out.println("Der Ordner existiert nicht!");
       }
        
        //Ich werde jetzt in der Klasse getDataSimpleNullCheck nicht mehr beispiele machen da es zu verstehen ist! Falls nicht einfach anfragen..
        
        
        //======================================================================================================================================================================================
        
        //So baut man eine Verbindung mit MySQL auf! Einfach immer aufrufen sobald man was auselen / speichern möchet! Falls eine verbindung besteht wird er sich nicht neu verbinden!
        BunnyAPI.Companion.getInstance().getMySQLSimpleHandle().connect("localhost", "Lobby", "3306", "Benutzername", "Passwort");
       
       //Beispiel erstellen einer Datenbank und Table
        BunnyAPI.Companion.getInstance().getMySQLSimpleHandle().createDatabase("Lobby"); //Datenbank anlegen
        BunnyAPI.Companion.getInstance().getMySQLSimpleHandle().createTable(
        "users",
        "id Int PRIMARY KEY AUTO_INCREMENT",
        "username String UNIQUE NOT NULL",
        "password String NOT NULL",
        "is_active Boolean DEFAULT TRUE"
        ); //Table anlegen
        
        //Beispiel: Einen Wert in der Datenbank speichern
        BunnyAPI.Companion.getInstance().getMySQLSimpleHandle().insertData(
        "users",
        "username, password, is_active",
        "'johndoe', 'password123', true"
        );
        
        //Beispiel: Daten lesen:
        try {
            // Direktes Auslesen aller Daten aus der Tabelle "users"
            ResultSet resultSet = BunnyAPI.Companion.getInstance().getMySQLSimpleHandle().readAllData("users");
            
            // Durch das ResultSet iterieren und Daten ausgeben
            while (resultSet != null && resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                ", Username: " + resultSet.getString("username") +
                ", Active: " + resultSet.getBoolean("is_active"));
            }
        } catch (SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        
        // Daten in der Tabelle "users" aktualisieren
        BunnyAPI.Companion.getInstance().getMySQLSimpleHandle().updateData(
        "users",
        "password = 'newpassword123'",
        "username = 'johndoe'"
        );
        
        // Nach einem Benutzer mit dem Benutzernamen "johndoe" in der Tabelle "users" suchen
        boolean exists = BunnyAPI.Companion.getInstance().getMySQLSimpleHandle().searchData("users", "username", "johndoe");
        
        // Ergebnis prüfen und ausgeben
        if (exists) {
            System.out.println("User 'johndoe' exists in the database.");
        } else {
            System.out.println("User 'johndoe' does not exist.");
        }
        
        // Tabelle "users" löschen
        BunnyAPI.Companion.getInstance().getMySQLSimpleHandle().deleteTable("users");
        
        // Datenbank "my_new_db" löschen
        BunnyAPI.Companion.getInstance().getMySQLSimpleHandle().deleteDatabase("my_new_db");
        
    }
}