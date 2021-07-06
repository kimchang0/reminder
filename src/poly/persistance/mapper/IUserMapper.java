package poly.persistance.mapper;

import config.Mapper;
import poly.dto.UserDTO;

@Mapper("UserMapper")
public interface IUserMapper {
	
	UserDTO getUserInfo(UserDTO tDTO);

	int UserSignUp(UserDTO tDTO);

	UserDTO idCheck(String userId);

	UserDTO emailCheck(String userEmail);

	int insertAuthNum(UserDTO uDTO);

	UserDTO authNumCheck(UserDTO uDTO);

	UserDTO Userinquire(UserDTO uDTO);

	int deleteUser(UserDTO uDTO);

	UserDTO getUserCorrection(UserDTO uDTO);

	int pwdChange(UserDTO uDTO);

	int updateInterest(UserDTO uDTO);

	int setUserCorrection(UserDTO uDTO);

	String test();

	UserDTO getUserAuth(UserDTO uDTO);

	UserDTO getInterest(UserDTO pDTO);

	int updateUserInterest(UserDTO pDTO);

	int reMakePW(UserDTO pDTO);

	UserDTO getUserEmail(UserDTO pDTO);

}
