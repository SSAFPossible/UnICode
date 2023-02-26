package org.SSAFP.UniCode.model.algorithm.repo;

import java.util.List;
import java.util.Map;

import org.SSAFP.UniCode.model.algorithm.dto.AlgoParam;
import org.SSAFP.UniCode.model.algorithm.dto.Algo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlgoRepo {
	
	public boolean registAlgo(Algo algo) throws Exception;
	
	public boolean modifyAlgo(Algo algo) throws Exception;
	
	public boolean deleteAlgo(int aid) throws Exception;
	
	public List<Algo> getAllAlgo(AlgoParam algoParam) throws Exception;
	
	public Algo getAlgo(int aid) throws Exception;
	
	public List<Algo> pickAlgo() throws Exception;
	
	public boolean updateSolved(Map<String, Integer> solvedInfo) throws Exception;
}