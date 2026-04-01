package infrastructure.database;

import java.util.ArrayList;
import domain.repositories.users.UsersDBInterface;
import fr.miniature.models.User;
import fr.miniature.models.Users;

public class UserRepository implements UsersDBInterface {
    private static UserRepository instance;
    //private DatabaseInterface database;
    private Users users = Users.getInstance();
    
    
    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }


    public void save(User newUser){
        users.addNewUser(newUser);
    }

    public User findByNameAndPassword(String name, String password){
        User user = users.getUserForConnexion(name, password);
        if(user == null) throw new Error("utilisateur inconnu.");

        return user;
    }

    public User findByID(String ID){
        return users.getUserByID(ID);
       // return (User) database.getEntityWithId(ID, EntityEnum.user);        
    }

    
    public ArrayList<User> find(){
        return users.find();
        /*ArrayList<User> usersList;
        usersList = database.getEntities(usersList);
        return usersList; */
    }

   // public ArrayList<T> find(int limit);
    public void deleteByID(String ID){
        // on surprime le user
    }

   
}
