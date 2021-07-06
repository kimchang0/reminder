package poly.service;

import poly.dto.UserDTO;

public interface IUserService {

	UserDTO getUserInfo(UserDTO tDTO);

	int UserSignUp(UserDTO tDTO);

	UserDTO idCheck(String userId);

	UserDTO emailCheck(String userEmail);

	UserDTO authNumCheck(UserDTO uDTO);

	int insertAuthNum(UserDTO uDTO);

	UserDTO Userinquire(UserDTO uDTO);

	int deleteUser(UserDTO uDTO);

	UserDTO getUserCorrection(UserDTO uDTO);

	int setUserCorrection(UserDTO uDTO);

	int pwdChange(UserDTO uDTO);

	int updateInterest(UserDTO uDTO);

	String test();

	UserDTO getUserAuth(UserDTO uDTO);

	UserDTO getInterest(UserDTO pDTO);

	int updateUserInterest(UserDTO pDTO);

	int reMakePW(UserDTO pDTO);

	UserDTO getUserEmail(UserDTO pDTO);


}
