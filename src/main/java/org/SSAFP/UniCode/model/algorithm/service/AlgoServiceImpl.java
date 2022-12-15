package org.SSAFP.UniCode.model.algorithm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.SSAFP.UniCode.model.algorithm.dto.Algo;
import org.SSAFP.UniCode.model.algorithm.dto.AlgoParam;
import org.SSAFP.UniCode.model.algorithm.repo.AlgoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlgoServiceImpl implements AlgoService {
	
	@Autowired
	AlgoRepo algoRepo;

	@Override
	@Transactional
	public boolean registAlgo(Algo algo) throws Exception {
		boolean regist = algoRepo.registAlgo(algo);
		if(!regist) {
			throw new Exception();
		}
		return true;
	}

	@Override
	@Transactional
	public boolean modifyAlgo(Algo algo) throws Exception {
		boolean modify = algoRepo.modifyAlgo(algo);
		if(!modify) {
			throw new Exception();
		}
		return true;
	}

	@Override
	@Transactional
	public boolean deleteAlgo(int aid) throws Exception {
		boolean delete = algoRepo.deleteAlgo(aid);
		if(!delete) {
			throw new Exception();
		}
		return true;
	}

	@Override
	public List<Algo> getAllAlgo(AlgoParam algoParam) throws Exception {
		return algoRepo.getAllAlgo(algoParam);
	}

	@Override
	public Algo getAlgo(int aid) throws Exception {
		return algoRepo.getAlgo(aid);
	}

	@Override
	@Transactional
	public List<Algo> pickAlgo() throws Exception {
		List<Algo> picked = algoRepo.pickAlgo();
		// 푼 문제 처리
		Map<String, Integer> solvedInfo = new HashMap<>();
		solvedInfo.put("solved", 1);
		for(Algo algo : picked) {
			solvedInfo.put("aid", algo.getAid());
			if(!algoRepo.updateSolved(solvedInfo)) {
				throw new Exception();
			}
			algo.setSolved(1);
		}
		return picked;
	}

	@Override
	@Transactional
	public boolean updateSolved(int aid) throws Exception {
		Map<String, Integer> solvedInfo = new HashMap<>();
		int solved = algoRepo.getAlgo(aid).getSolved();
		solvedInfo.put("aid", aid);		
		if(solved == 1) { // 푼 문제의 경우
			solvedInfo.put("solved", 0);
			algoRepo.updateSolved(solvedInfo);
			return false;
		} else { // 안 푼 문제의 경우
			solvedInfo.put("solved", 1);
			algoRepo.updateSolved(solvedInfo);
			return true;
		}
	}
}