package cz.metacentrum.perun.core.api;

import java.util.List;

import cz.metacentrum.perun.core.api.exceptions.*;

/**
 * UsersManager manages users.
 *
 * @author Michal Prochazka
 * @author Slavek Licehammer
 * @author Zora Sebestianova
 */
public interface UsersManager {

  /**
   * Returns user by his login in external source and external source.
   *
   * @param perunSession     
   * @param userExtSource
   * @return selected user or throws UserNotExistsException in case the user doesn't exists
   * @throws InternalErrorException
   * @throws UserNotExistsException
   * @throws UserExtSourceNotExistsException
   * @throws PrivilegeException
   */
  User getUserByUserExtSource(PerunSession perunSession, UserExtSource userExtSource) throws InternalErrorException, UserNotExistsException, UserExtSourceNotExistsException, PrivilegeException;

  /**
   * Returns user based on one of the userExtSource.
   * 
   * @param perunSession
   * @param userExtSources
   * @return user
   * @throws InternalErrorException
   * @throws UserNotExistsException
   * @throws PrivilegeException
   */
  User getUserByUserExtSources(PerunSession perunSession, List<UserExtSource> userExtSources) throws InternalErrorException, UserNotExistsException, PrivilegeException;

  /**
   * Return all serviceUsers who are owned by the user
   * 
   * @param sess
   * @param user the user
   * @return list of service users who are owned by the user
   * @throws InternalErrorException 
   * @throws UserNotExistsException
   * @throws PrivilegeException 
   * @throws NotServiceUserExpectedException when the user is service User
   */
  List<User> getServiceUsersByUser(PerunSession sess, User user) throws InternalErrorException, UserNotExistsException, PrivilegeException, NotServiceUserExpectedException;
  
  /**
   * Return all users who owns the serviceUser
   * 
   * @param sess
   * @param serviceUser the service User
   * @return list of user who owns the serviceUser
   * @throws InternalErrorException 
   * @throws UserNotExistsException
   * @throws PrivilegeException 
   * @throws ServiceUserExpectedException when the serviceUser is not really service user (is it normal user)
   */
  List<User> getUsersByServiceUser(PerunSession sess, User serviceUser) throws InternalErrorException, UserNotExistsException, PrivilegeException, ServiceUserExpectedException;
  
  /**
   * Remove serviceUser owner (the user)
   * Only disable ownership of user and serviceUser
   * 
   * @param sess
   * @param user the user
   * @param serviceUser the serviceUser
   * @throws InternalErrorException 
   * @throws UserNotExistsException
   * @throws PrivilegeException 
   * @throws ServiceUserExpectedException when the serviceUser is not really service user (is it normal user)
   * @throws NotServiceUserExpectedException when the user is service User
   * @throws RelationNotExistsException if there is no such user (the user) to remove 
   * @throws ServiceUserMustHaveOwnerException if there is the last user to remove
   * @throws cz.metacentrum.perun.core.api.exceptions.ServiceUserOwnerAlreadyRemovedException if there are 0 rows affected by removing from DB
   */
  void removeServiceUserOwner(PerunSession sess, User user, User serviceUser) throws InternalErrorException, UserNotExistsException, PrivilegeException, NotServiceUserExpectedException, ServiceUserExpectedException, RelationNotExistsException, ServiceUserMustHaveOwnerException, ServiceUserOwnerAlreadyRemovedException;
  
  /**
   * Add serviceUser owner (the user)
   * If not exists, create new ownership.
   * If exists, only enable ownership for user and serviceUser
   * 
   * @param sess
   * @param user the user
   * @param serviceUser the serviceUser
   * @throws InternalErrorException
   * @throws UserNotExistsException
   * @throws PrivilegeException 
   * @throws ServiceUserExpectedException when the serviceUser is not really service user (is it normal user)
   * @throws NotServiceUserExpectedException when the user is service User
   * @throws RelationExistsException If there is such user (the user) who try to add
   */
  void addServiceUserOwner(PerunSession sess, User user, User serviceUser) throws InternalErrorException, UserNotExistsException, PrivilegeException, NotServiceUserExpectedException, ServiceUserExpectedException, RelationExistsException;
  
  /**
   * Return all service Users (only service users)
   * 
   * @param sess
   * @return list of all service users in perun
   * @throws InternalErrorException
   * @throws PrivilegeException 
   */
  List<User> getServiceUsers(PerunSession sess) throws InternalErrorException, PrivilegeException;
  
  /**
   * Returns user by his/her id.
   *
   * @param perunSession 
   * @param id 
   * @return user
   * @throws UserNotExistsException
   * @throws InternalErrorException
   * @throws PrivilegeException
   */
  User getUserById(PerunSession perunSession, int id) throws InternalErrorException, UserNotExistsException, PrivilegeException;

  /**
   * Returns user by VO member.
   *
   * @param perunSession 
   * @param member
   * @return user
   * @throws UserNotExistsException
   * @throws InternalErrorException
   * @throws MemberNotExistsException
   * @throws PrivilegeException
   */
  User getUserByMember(PerunSession perunSession, Member member) throws InternalErrorException, MemberNotExistsException, PrivilegeException;

  /**
   * Get user by extSourceName and extSourceLogin
   * 
   * @param sess
   * @param extSourceName
   * @param extLogin
   * @return user
   * @throws ExtSourceNotExistsException
   * @throws UserExtSourceNotExistsException
   * @throws UserNotExistsException
   * @throws InternalErrorException
   * @throws PrivilegeException
   */
  User getUserByExtSourceNameAndExtLogin(PerunSession sess, String extSourceName, String extLogin) throws ExtSourceNotExistsException, UserExtSourceNotExistsException, UserNotExistsException, InternalErrorException, PrivilegeException;
  
  /**
   * Returns all users (included service users).
   * 
   * @param sess
   * @return list of all users
   * @throws InternalErrorException
   */
  List<User> getUsers(PerunSession sess) throws InternalErrorException, PrivilegeException;
  
  /**
   * Get User to RichUser without attributes.
   * @param sess
   * @param user
   * @return RichUser
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException
   */
  RichUser getRichUser(PerunSession sess, User user) throws InternalErrorException, PrivilegeException, UserNotExistsException;  
  
  /**
   * Get User to RichUser with attributes.
   * @param sess
   * @param user
   * @return RichUser
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException
   */
  RichUser getRichUserWithAttributes(PerunSession sess, User user) throws InternalErrorException, PrivilegeException, UserNotExistsException;
  
  /**
   * Get All richUsers with or without serviceUsers.
   * If includedServiceUsers is true, you got all Users included serviceUsers
   * If includedServiceUsers is false, you get all Users without serviceUsers
   * 
   * !!! This method get all RichUsers without Attributes !!!
   * 
   * @param sess
   * @param includedServiceUsers true or false if you want or dont want get serviceUsers too
   * @return list of RichUsers
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException 
   */
  List<RichUser> getAllRichUsers(PerunSession sess, boolean includedServiceUsers) throws InternalErrorException, PrivilegeException, UserNotExistsException;
  
  /**
   * Get All richUsers with or without serviceUsers.
   * If includedServiceUsers is true, you got all Users included serviceUsers
   * If includedServiceUsers is false, you get all Users without serviceUsers
   * 
   * This method get all RichUsers included Attributes.
   * 
   * @param sess
   * @param includedServiceUsers true or false if you want or dont want get serviceUsers too
   * @return list of RichUsers
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException 
   */
  List<RichUser> getAllRichUsersWithAttributes(PerunSession sess, boolean includedServiceUsers) throws InternalErrorException, PrivilegeException, UserNotExistsException;

  /**
   * From Users makes RichUsers without attributes.
   * 
   * @param sess
   * @param users users to convert
   * @return list of richUsers
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException 
   */
  List<RichUser> getRichUsersFromListOfUsers(PerunSession sess, List<User> users) throws InternalErrorException, PrivilegeException, UserNotExistsException;
  
  /**
   * From Users makes RichUsers with attributes.
   * 
   * @param sess
   * @param users users to convert
   * @return list of richUsers
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException 
   */
  List<RichUser> getRichUsersWithAttributesFromListOfUsers(PerunSession sess, List<User> users) throws InternalErrorException, PrivilegeException, UserNotExistsException;  
  
  /**
   *  Inserts user into DB.
   *
   * @param perunSession   
   * @param user
   * @throws InternalErrorException
   * @throws PrivilegeException
   */
  @Deprecated
  User createUser(PerunSession perunSession, User user) throws InternalErrorException, PrivilegeException;

  /**
   *  Deletes user.
   *
   * @param perunSession        
   * @param user
   * @throws InternalErrorException
   * @throws UserNotExistsException
   * @throws PrivilegeException  
   * @throws RelationExistsException if user has some members assigned
   * @throws MemberAlreadyRemovedException if there is at least 1 member deleted but not affected by deleting from DB
   * @throws UserAlreadyRemovedException if there are no rows affected by deleting user in DB
   * @throws ServiceUserAlreadyRemovedException if there are no rows affected  by deleting serviceUser in DB
   */
  void deleteUser(PerunSession perunSession, User user) throws InternalErrorException, UserNotExistsException, PrivilegeException, RelationExistsException, MemberAlreadyRemovedException, UserAlreadyRemovedException, ServiceUserAlreadyRemovedException;

  /**
   *  Deletes user. If forceDelete is true, then removes also associeted members.
   *
   * @param perunSession        
   * @param user
   * @param forceDelete if true, deletes also all members if they are assigned to the user
   * @throws InternalErrorException
   * @throws UserNotExistsException
   * @throws RelationExistsException if forceDelete is false and the user has some members assigned
   * @throws MemberAlreadyRemovedException if there is at least 1 member deleted but not affected by deleting from DB
   * @throws UserAlreadyRemovedException if there are no rows affected by deleting user in DB
   * @throws ServiceUserAlreadyRemovedException if there are no rows affected  by deleting serviceUser in DB
   */
  void deleteUser(PerunSession perunSession, User user, boolean forceDelete) throws InternalErrorException, UserNotExistsException, PrivilegeException, RelationExistsException, MemberAlreadyRemovedException, UserAlreadyRemovedException, ServiceUserAlreadyRemovedException;

  /**
   *  Updates users data in DB.
   *
   * @param perunSession       
   * @param user
   * @return updated user
   * @throws InternalErrorException
   * @throws UserNotExistsException
   * @throws PrivilegeException
   */
  User updateUser(PerunSession perunSession, User user) throws InternalErrorException, UserNotExistsException, PrivilegeException;

    /**
     *  Updates titles before/after name of user.
     *
     *  New titles must be set inside User object.
     *  Setting any title to null will remove title from name.
     *  Other user's properties are ignored.
     *
     * @param perunSession
     * @param user
     * @return updated user with new titles before/after name
     * @throws InternalErrorException
     * @throws UserNotExistsException
     * @throws PrivilegeException
     */
    User updateNameTitles(PerunSession perunSession, User user) throws InternalErrorException, UserNotExistsException, PrivilegeException;

    /**
   *  Updates user's userExtSource in DB.
   *
   * @param perunSession
   * @param userExtSource
   * @return updated userExtSource
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserExtSourceNotExistsException
   */
  UserExtSource updateUserExtSource(PerunSession perunSession, UserExtSource userExtSource) throws InternalErrorException, UserExtSourceNotExistsException, PrivilegeException;
  
  /**
   * Gets list of all user's external sources of the user.
   * 
   * @param perunSession       
   * @param user
   * @return list of user's external sources
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException
   */
  List<UserExtSource> getUserExtSources(PerunSession perunSession, User user) throws InternalErrorException, UserNotExistsException, PrivilegeException;

  /**
   * Get the user ext source by its id.
   * 
   * @param sess
   * @param id
   * @return user external source for the id
   * @throws InternalErrorException
   * @throws UserExtSourceNotExistsException
   * @throws PrivilegeException
   */
  UserExtSource getUserExtSourceById(PerunSession sess, int id) throws InternalErrorException, UserExtSourceNotExistsException, PrivilegeException;
  
  /**
   * Adds user's external sources.
   * 
   * @param perunSession       
   * @param user
   * @param userExtSource
   * @return      user external auth object with newly generated ID
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException
   * @throws UserExtSourceExistsException
   */
  UserExtSource addUserExtSource(PerunSession perunSession, User user, UserExtSource userExtSource) throws InternalErrorException, UserNotExistsException, PrivilegeException, UserExtSourceExistsException;

  /**
   * Removes user's external sources.
   * 
   * @param perunSession       
   * @param user
   * @param userExtSource
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserExtSourceNotExistsException
   * @throws UserNotExistsException
   * @throws UserExtSourceAlreadyRemovedException if there are 0 rows affected by deleting from DB
   */
  void removeUserExtSource(PerunSession perunSession, User user, UserExtSource userExtSource) throws InternalErrorException, UserNotExistsException, UserExtSourceNotExistsException, PrivilegeException, UserExtSourceAlreadyRemovedException;

  /**
   * Gets user's external source by the user's external login and external source.
   * 
   * @param perunSession
   * @param source
   * @param extLogin
   * @return user external source object
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws ExtSourceNotExistsException
   * @throws UserExtSourceNotExistsException
   */
  UserExtSource getUserExtSourceByExtLogin(PerunSession perunSession, ExtSource source, String extLogin) throws InternalErrorException, 
    PrivilegeException, ExtSourceNotExistsException, UserExtSourceNotExistsException;

  /**
   * Returns list of VOs, where the user is an Administrator.
   * 
   * @param perunSession
   * @param user
   * @return list of VOs, where the user is an Administrator.
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException
   */
  List<Vo> getVosWhereUserIsAdmin(PerunSession perunSession, User user) throws InternalErrorException, UserNotExistsException, PrivilegeException;

  /**
   * Returns list of Groups, where the user is an Administrator.
   * 
   * @param perunSession
   * @param user
   * @return list of Groups, where the user is an Administrator.
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException
   */
  List<Group> getGroupsWhereUserIsAdmin(PerunSession perunSession, User user) throws InternalErrorException, UserNotExistsException, PrivilegeException;

  /**
   * Returns list of VOs, where the user is a member.
   * 
   * @param perunSession
   * @param user
   * @return list of VOs, where the user is a member.
   * @throws InternalErrorException
   */
  List<Vo> getVosWhereUserIsMember(PerunSession perunSession, User user) throws InternalErrorException, UserNotExistsException, PrivilegeException;
  
  /**
   * Get all resources from the facility which have the user access on.
   * 
   * @param sess
   * @param facility
   * @param user
   * @return list of resources which have the user acess on
   * 
   * @throws InternalErrorException
   * @throws FacilityNotExistsException
   * @throws UserNotExistsException
   * @throws PrivilegeException
   */
  List<Resource> getAllowedResources(PerunSession sess, Facility facility, User user) throws InternalErrorException, FacilityNotExistsException, UserNotExistsException, PrivilegeException;

  /**
   * Get all resources which have the user access on.
   * 
   * @param sess
   * @param user
   * @return list of resources which have the user acess on
   * 
   * @throws InternalErrorException
   * @throws UserNotExistsException
   * @throws PrivilegeException
   */
  List<Resource> getAllowedResources(PerunSession sess, User user) throws InternalErrorException, UserNotExistsException, PrivilegeException;

  /**
   * Get all rich resources which have the user assigned.
   *
   * @param sess
   * @param user
   * @return list of rich resources which have the user assigned
   *
   * @throws InternalErrorException
   * @throws UserNotExistsException
   * @throws PrivilegeException
   */
  List<RichResource> getAssignedRichResources(PerunSession sess, User user) throws InternalErrorException, UserNotExistsException, PrivilegeException;

    /**
   * Returns list of users who matches the searchString, searching name, email, logins.
   * 
   * @param sess
   * @param searchString
   * @return list of users
   * @throws InternalErrorException
   */
  List<User> findUsers(PerunSession sess, String searchString) throws InternalErrorException, PrivilegeException;
  
  /**
   * Returns list of RichUsers with attributes who matches the searchString, searching name, email, logins.
   * 
   * @param sess
   * @param searchString
   * @return list of RichUsers
   * @throws InternalErrorException
   * @throws UserNotExistsException
   * @throws PrivilegeException
   */
  List<RichUser> findRichUsers(PerunSession sess, String searchString) throws InternalErrorException, UserNotExistsException, PrivilegeException;
  
  /**
   * Return list of users who matches the searchString, searching name, email and logins 
   * and are not member in specific VO.
   * 
   * @param sess
   * @param vo
   * @param searchString
   * @return
   * @throws InternalErrorException
   * @throws VoNotExistsException
   * @throws PrivilegeException 
   */
  List<User> getUsersWithoutSpecificVo(PerunSession sess, Vo vo, String searchString) throws InternalErrorException, VoNotExistsException, PrivilegeException;
  
    
  /**
   * Returns list of users who matches the searchString
   * 
   * @param sess
   * @param searchString
   * @return list of users
   * @throws InternalErrorException
   */
  List<User> findUsersByName(PerunSession sess, String searchString) throws InternalErrorException, PrivilegeException;
  
  /**
   * Returns list of users who matches the fields.
   * 
   * @param sess
   * @param titleBefore
   * @param firstName
   * @param middleName
   * @param lastName
   * @param titleAfter
   * @return list of users
   * @throws InternalErrorException
   * @throws PrivilegeException
   */
  List<User> findUsersByName(PerunSession sess, String titleBefore, String firstName, String middleName, String lastName, String titleAfter) throws InternalErrorException, PrivilegeException;
  
  /**
   * Checks if the login is available in the namespace.
   * 
   * @param sess
   * @param loginNamespace in which the login will be checked (provide only the name of the namespace, not the whole attribute name)
   * @param login to be checked
   * @return true if login available, false otherwise
   * @throws InternalErrorException
   * @throws PrivilegeException
   */
  boolean isLoginAvailable(PerunSession sess, String loginNamespace, String login) throws InternalErrorException, PrivilegeException;
  
  
  /**
   * Returns all users who have set the attribute with the value. Searching only def and opt attributes.
   * 
   * @param sess
   * @param attribute
   * @return list of users
   * @throws InternalErrorException
   * @throws PrivilegeException
   */
  List<User> getUsersByAttribute(PerunSession sess, Attribute attribute) throws InternalErrorException, PrivilegeException;
 
  /**
   * Returns all RichUsers with attributes who are not member of any VO.
   * 
   * @param sess
   * @return list of richUsers who are not member of any VO
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException
   */
  List<RichUser> getRichUsersWithoutVoAssigned(PerunSession sess) throws InternalErrorException, UserNotExistsException, PrivilegeException;
  
  /**
   * Returns all users who have set the attribute with the value. Searching by attributeName. Searching only def and opt attributes.
   * Can find only attributes with String Value by this way! (not Integer, Map or List)
   * 
   * @param sess
   * @param attributeName
   * @param attributeValue
   * @return list of users
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws AttributeNotExistsException
   */
  List<User> getUsersByAttribute(PerunSession sess, String attributeName, String attributeValue) throws InternalErrorException, PrivilegeException, AttributeNotExistsException;
  
  /**
   * Returns all users who have the attribute with the value. attributeValue is not converted to the attribute type, it is always type of String.  
   * 
   * @param sess
   * @param attributeName
   * @param attributeValue
   * @return list of users
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws AttributeNotExistsException
   */
  List<User> getUsersByAttributeValue(PerunSession sess, String attributeName, String attributeValue) throws InternalErrorException, PrivilegeException, AttributeNotExistsException;
  
  /**
   * Returns all users who are not member of any VO.
   * 
   * @param sess
   * @return list of users who are not member of any VO
   * @throws InternalErrorException
   * @throws PrivilegeException
   */
  List<User> getUsersWithoutVoAssigned(PerunSession sess) throws InternalErrorException, PrivilegeException;
  
  /**
   * Adds PERUNADMIN role to the user.
   * 
   * @param sess
   * @param user
   * @throws InternalErrorException
   * @throws NotServiceUserExpectedException if the user is service User
   * @throws PrivilegeException
   * @throws UserNotExistsException
   */
  void makeUserPerunAdmin(PerunSession sess, User user) throws InternalErrorException, PrivilegeException, UserNotExistsException, NotServiceUserExpectedException;
  
  /**
   * Returns true if the user is PERUNADMIN.
   * 
   * @param sess
   * @param user
   * @return true if the user is PERUNADMIN, false otherwise.
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException
   */
  boolean isUserPerunAdmin(PerunSession sess, User user) throws InternalErrorException, PrivilegeException, UserNotExistsException;
  
  /**
   * Changes user password in defined login-namespace. If checkOldPassword is true, then ask autnetication system if old password is correct.
   * 
   * @param sess
   * @param user
   * @param loginNamespace
   * @param oldPassword
   * @param newPassword
   * @param checkOldPassword
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException
   * @throws LoginNotExistsException
   * @throws PasswordDoesntMatchException
   * @throws PasswordChangeFailedException
   */
  void changePassword(PerunSession sess, User user, String loginNamespace, String oldPassword, String newPassword, boolean checkOldPassword)
      throws InternalErrorException, PrivilegeException, UserNotExistsException, LoginNotExistsException, PasswordDoesntMatchException, PasswordChangeFailedException;
  
  /**
   * Creates the password in external system. User must not exists.
   * 
   * @param sess
   * @param userLogin string representation of the userLogin
   * @param loginNamespace
   * @param password
   * @throws InternalErrorException
   * @throws PasswordCreationFailedException
   */
  @Deprecated
  void createPassword(PerunSession sess, String userLogin, String loginNamespace, String password) 
      throws InternalErrorException, PasswordCreationFailedException, PrivilegeException;
  
  /**
   * Creates the password in external system. User must exists.
   * 
   * @param sess
   * @param user
   * @param loginNamespace
   * @param password
   * @throws InternalErrorException
   * @throws PasswordCreationFailedException
   * @throws UserNotExistsException
   * @throws LoginNotExistsException
   * @throws PrivilegeException
   */
  @Deprecated
  void createPassword(PerunSession sess, User user, String loginNamespace, String password) 
      throws InternalErrorException, PasswordCreationFailedException, PrivilegeException, UserNotExistsException, LoginNotExistsException;

  /**
   * Reserves random password in external system. User must not exists.
   * 
   * @param sess
   * @param user
   * @param loginNamespace
   * @throws InternalErrorException
   * @throws PasswordCreationFailedException
   * @throws UserNotExistsException
   * @throws LoginNotExistsException
   */
  void reserveRandomPassword(PerunSession sess, User user, String loginNamespace) throws InternalErrorException, PasswordCreationFailedException, PrivilegeException, UserNotExistsException, LoginNotExistsException;
  
  /**
   * Reserves the password in external system. User must not exists.
   * 
   * @param sess
   * @param userLogin string representation of the userLogin
   * @param loginNamespace
   * @param password
   * @throws InternalErrorException
   * @throws PasswordCreationFailedException
   */
  void reservePassword(PerunSession sess, String userLogin, String loginNamespace, String password) 
      throws InternalErrorException, PasswordCreationFailedException, PrivilegeException;
  
  /**
   * Reserves the password in external system. User must exists.
   * 
   * @param sess
   * @param user
   * @param loginNamespace
   * @param password
   * @throws InternalErrorException
   * @throws PasswordCreationFailedException
   * @throws UserNotExistsException
   * @throws LoginNotExistsException
   * @throws PrivilegeException
   */
  void reservePassword(PerunSession sess, User user, String loginNamespace, String password) 
      throws InternalErrorException, PasswordCreationFailedException, PrivilegeException, UserNotExistsException, LoginNotExistsException;
  
  /**
   * Validates the password in external system. User must not exists.
   * 
   * @param sess
   * @param userLogin string representation of the userLogin
   * @param loginNamespace
   * @throws InternalErrorException
   * @throws PasswordCreationFailedException
   */
  void validatePassword(PerunSession sess, String userLogin, String loginNamespace) 
      throws InternalErrorException, PasswordCreationFailedException, PrivilegeException;

  /**
   * Validates the password in external system and set user extSources and extSource related attributes. User must exists.
   * 
   * @param sess
   * @param user
   * @param userLogin
   * @param loginNamespace
   * 
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws PasswordCreationFailedException
   * @throws LoginNotExistsException
   * @throws ExtSourceNotExistsException
   * @throws WrongAttributeValueException
   * @throws WrongReferenceAttributeValueException
   */
  public void validatePasswordAndSetExtSources(PerunSession sess, User user, String userLogin, String loginNamespace) throws InternalErrorException, PrivilegeException, PasswordCreationFailedException, LoginNotExistsException, ExtSourceNotExistsException, WrongAttributeValueException, WrongReferenceAttributeValueException;

  
  /**
   * Validates the password in external system. User must exists.
   * 
   * @param sess
   * @param user
   * @param loginNamespace
   * @throws InternalErrorException
   * @throws PasswordCreationFailedException
   * @throws UserNotExistsException
   * @throws LoginNotExistsException
   * @throws PrivilegeException
   */
  void validatePassword(PerunSession sess, User user, String loginNamespace) 
      throws InternalErrorException, PasswordCreationFailedException, PrivilegeException, UserNotExistsException, LoginNotExistsException;
  
  /**
   * Deletes password in external system. User must not exists.
   * 
   * @param sess
   * @param userLogin
   * @param loginNamespace
   * @throws InternalErrorException
   * @throws PasswordDeletionFailedException
   * @throws LoginNotExistsException
   */
  void deletePassword(PerunSession sess, String userLogin, String loginNamespace) 
      throws InternalErrorException, PasswordDeletionFailedException, PrivilegeException, LoginNotExistsException;
  
  /**
   * Get All richUsers with or without serviceUsers.
   * If includedServiceUsers is true, you got all Users included serviceUsers
   * If includedServiceUsers is false, you get all Users without serviceUsers
   * 
   * This method get all RichUsers included selected Attributes.
   * 
   * @param sess
   * @param attrsNames
   * @param includedServiceUsers true or false if you want or dont want get serviceUsers too
   * @return list of RichUsers
   * @throws InternalErrorException
   * @throws PrivilegeException
   * @throws UserNotExistsException 
   */
  List<RichUser> getAllRichUsersWithAttributes(PerunSession sess, boolean includedServiceUsers,List<String> attrsNames)
    throws InternalErrorException, PrivilegeException, UserNotExistsException;
     
/**
   * Returns list of RichUsers with attributes who matches the searchString, searching name, email, logins.
   * 
   * @param sess
   * @param searchString
   * @param attrNames
   * @return list of RichUsers with selected attributes
   * @throws InternalErrorException
   * @throws UserNotExistsException
   * @throws PrivilegeException
   */
  List<RichUser> findRichUsersWithAttributes(PerunSession sess, String searchString, List<String> attrNames) 
    throws InternalErrorException, UserNotExistsException, PrivilegeException;

  /**
   * Returns list of RichUsers which are not members of any VO and with selected attributes
   * 
   * @param sess
   * @param attrNames
   * @return list of RichUsers with selected attributes
   * @throws InternalErrorException
   * @throws UserNotExistsException
   * @throws VoNotExistsException
   * @throws PrivilegeException
   */
  List<RichUser> getRichUsersWithoutVoWithAttributes(PerunSession sess, List<String> attrNames)
    throws InternalErrorException, VoNotExistsException, UserNotExistsException, PrivilegeException;

  /**
   * Return list of RichUsers who matches the searchString, searching name, email and logins 
   * and are not member in specific VO and contain selected attributes.
   * 
   * @param sess
   * @param vo
   * @param searchString
   * @param attrsName 
   * @return list of RichUsers
   * @throws InternalErrorException
   * @throws UserNotExistsException
   * @throws VoNotExistsException
   * @throws PrivilegeException 
   */
  List<RichUser> findRichUsersWithoutSpecificVoWithAttributes(PerunSession sess, Vo vo, String searchString, List<String> attrsName)
    throws InternalErrorException, UserNotExistsException, VoNotExistsException, PrivilegeException;

    /**
     * Allow users to manually add login in supported namespace if same login is not reserved
     *
     * @param sess
     * @param user
     * @param loginNamespace
     * @param login
     * @throws InternalErrorException
     * @throws PrivilegeException
     * @throws UserNotExistsException
     * @throws LoginExistsException
     */
    void setLogin(PerunSession sess, User user, String loginNamespace, String login) throws InternalErrorException, PrivilegeException, UserNotExistsException, LoginExistsException;

    /**
     * Request change of user's preferred email address.
     * Change in attribute value is not done, until email
     * address is verified by link in email notice.
     * (urn:perun:user:attribute-def:def:preferredMail)
     *
     * @param sess PerunSession
     * @param url base URL of running perun instance passed from RPC.
     * @param user User to request preferred email change for
     * @param email new email address
     *
     * @throws InternalErrorException
     * @throws PrivilegeException
     * @throws UserNotExistsException
     */
    void requestPreferredEmailChange(PerunSession sess, String url, User user, String email) throws InternalErrorException, PrivilegeException, UserNotExistsException;

    /**
     * Validate change of user's preferred email address.
     * New email address is set as value of
     * urn:perun:user:attribute-def:def:preferredMail attribute.
     *
     * @param sess PerunSession
     * @param user User to validate email address for
     * @param i decrypted parameter
     * @param m encrypted parameter
     *
     * @throws InternalErrorException
     * @throws PrivilegeException
     * @throws UserNotExistsException
     * @throws WrongAttributeAssignmentException
     * @throws AttributeNotExistsException
     * @throws WrongReferenceAttributeValueException
     * @throws WrongAttributeValueException
     *
     * @return String return new preferred email
     */
    String validatePreferredEmailChange(PerunSession sess, User user, String i, String m) throws InternalErrorException, PrivilegeException, UserNotExistsException, WrongAttributeAssignmentException, AttributeNotExistsException, WrongReferenceAttributeValueException, WrongAttributeValueException;

    /**
     * Return list of email addresses of user, which are
     * awaiting validation and are inside time window
     * for validation.
     *
     * If there is no preferred email change request pending
     * or requests are outside time window for validation,
     * returns empty list.
     *
     * @param sess PerunSession
     * @param user User to check pending request for
     *
     * @throws InternalErrorException
     * @throws PrivilegeException
     * @throws UserNotExistsException
     * @throws WrongAttributeAssignmentException
     * @throws AttributeNotExistsException
     *
     * @return List<String> user's email addresses pending validation
     */
    List<String> getPendingPreferredEmailChanges(PerunSession sess, User user) throws InternalErrorException, PrivilegeException, UserNotExistsException, WrongAttributeAssignmentException, AttributeNotExistsException;


}
