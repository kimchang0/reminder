package poly.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import poly.dto.UserDTO;
import poly.persistance.mapper.IUserMapper;
import poly.service.IUserService;

@Service("UserService")
public class UserService implements IUserService {

	@Resource(name = "UserMapper")
	private IUserMapper userMapper;

	@Override
	public UserDTO getUserInfo(UserDTO tDTO) {
		return userMapper.getUserInfo(tDTO);
	}

	@Override
	public int UserSignUp(UserDTO tDTO) {
		return userMapper.UserSignUp(tDTO);
	}

	@Override
	public UserDTO idCheck(String userId) {
		return userMapper.idCheck(userId);
	}

	@Override
	public UserDTO emailCheck(String userEmail) {
		return userMapper.emailCheck(userEmail);
	}

	@Override
	public int insertAuthNum(UserDTO uDTO) {

		return userMapper.insertAuthNum(uDTO);
	}

	@Override
	public UserDTO authNumCheck(UserDTO uDTO) {

		return userMapper.authNumCheck(uDTO);
	}

	@Override
	public UserDTO Userinquire(UserDTO uDTO) {
		return userMapper.Userinquire(uDTO);
	}

	@Override
	public int deleteUser(UserDTO uDTO) {

		return userMapper.deleteUser(uDTO);
	}

	@Override
	public int setUserCorrection(UserDTO uDTO) {

		return userMapper.setUserCorrection(uDTO);
	}

	@Override
	public UserDTO getUserCorrection(UserDTO uDTO) {

		return userMapper.getUserCorrection(uDTO);
	}

	@Override
	public int pwdChange(UserDTO uDTO) {

		return userMapper.pwdChange(uDTO);
	}

	@Override
	public int updateInterest(UserDTO uDTO) {

		return userMapper.updateInterest(uDTO);

	}

	@Override
	public String test() {
		return userMapper.test();
	}

	@Override
	public UserDTO getUserAuth(UserDTO uDTO) {
		
		return userMapper.getUserAuth(uDTO);
	}

	@Override
	public UserDTO getInterest(UserDTO pDTO) {
		
		return userMapper.getInterest(pDTO);
	}

	@Override
	public int updateUserInterest(UserDTO pDTO) {
		
		return userMapper.updateUserInterest(pDTO);
	}

	@Override
	public int reMakePW(UserDTO pDTO) {
		
		return userMapper.reMakePW(pDTO);
	}

	@Override
	public UserDTO getUserEmail(UserDTO pDTO) {
		
		return userMapper.getUserEmail(pDTO);
	}

	
}
