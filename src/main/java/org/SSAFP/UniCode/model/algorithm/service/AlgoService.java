package org.SSAFP.UniCode.model.algorithm.service;

import java.util.List;

import org.SSAFP.UniCode.model.algorithm.dto.Algo;
import org.SSAFP.UniCode.model.algorithm.dto.AlgoParam;
import org.springframework.stereotype.Service;

public interface AlgoService {
	
public boolean registAlgo(Algo algo) throws Exception;
	
	public boolean modifyAlgo(Algo algo) throws Exception;
	
	public boolean deleteAlgo(int aid) throws Exception;
	
	public List<Algo> getAllAlgo(AlgoParam algoParam) throws Exception;
	
	public Algo getAlgo(int aid) throws Exception;
	
	public List<Algo> pickAlgo() throws Exception;
	
	public boolean updateSolved(int aid) throws Exception;
}
