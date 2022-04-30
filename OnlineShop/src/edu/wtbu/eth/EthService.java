package edu.wtbu.eth;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.utils.Convert;

import edu.wtbu.pojo.OrderDetail;

public class EthService {
	private static String url = "http://127.0.0.1:8545";
	private static Admin admin = null;
	private static Web3j web3j = null;
	private static String sysPassword = "123456";
	private static String sellerPassword = "123456";
	private static String bincode = "0x6060604052341561000f57600080fd5b6040516109fb3803806109fb83398101604052808051820191906020018051906020019091908051820191906020018051820191906020018051820191906020018051820191905050856000908051906020019061006e9291906100e5565b5084600181905550836002908051906020019061008c9291906100e5565b5082600390805190602001906100a39291906100e5565b5081600490805190602001906100ba9291906100e5565b5080600590805190602001906100d19291906100e5565b50600060078190555050505050505061018a565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061012657805160ff1916838001178555610154565b82800160010185558215610154579182015b82811115610153578251825591602001919060010190610138565b5b5090506101619190610165565b5090565b61018791905b8082111561018357600081600090555060010161016b565b5090565b90565b610862806101996000396000f30060606040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806301efb6c814610051578063966a7dca14610309575b600080fd5b341561005c57600080fd5b610064610366565b6040518080602001898152602001806020018060200180602001806020018060200188815260200187810387528f818151815260200191508051906020019080838360005b838110156100c45780820151818401526020810190506100a9565b50505050905090810190601f1680156100f15780820380516001836020036101000a031916815260200191505b5087810386528d818151815260200191508051906020019080838360005b8381101561012a57808201518184015260208101905061010f565b50505050905090810190601f1680156101575780820380516001836020036101000a031916815260200191505b5087810385528c818151815260200191508051906020019080838360005b83811015610190578082015181840152602081019050610175565b50505050905090810190601f1680156101bd5780820380516001836020036101000a031916815260200191505b5087810384528b818151815260200191508051906020019080838360005b838110156101f65780820151818401526020810190506101db565b50505050905090810190601f1680156102235780820380516001836020036101000a031916815260200191505b5087810383528a818151815260200191508051906020019080838360005b8381101561025c578082015181840152602081019050610241565b50505050905090810190601f1680156102895780820380516001836020036101000a031916815260200191505b50878103825289818151815260200191508051906020019080838360005b838110156102c25780820151818401526020810190506102a7565b50505050905090810190601f1680156102ef5780820380516001836020036101000a031916815260200191505b509e50505050505050505050505050505060405180910390f35b341561031457600080fd5b610364600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190505061075b565b005b61036e61077d565b600061037861077d565b61038061077d565b61038861077d565b61039061077d565b61039861077d565b6000808054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561042f5780601f106104045761010080835404028352916020019161042f565b820191906000526020600020905b81548152906001019060200180831161041257829003601f168201915b50505050509750600154965060028054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104d15780601f106104a6576101008083540402835291602001916104d1565b820191906000526020600020905b8154815290600101906020018083116104b457829003601f168201915b5050505050955060038054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561056e5780601f106105435761010080835404028352916020019161056e565b820191906000526020600020905b81548152906001019060200180831161055157829003601f168201915b5050505050945060048054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561060b5780601f106105e05761010080835404028352916020019161060b565b820191906000526020600020905b8154815290600101906020018083116105ee57829003601f168201915b5050505050935060058054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106a85780601f1061067d576101008083540402835291602001916106a8565b820191906000526020600020905b81548152906001019060200180831161068b57829003601f168201915b5050505050925060068054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107455780601f1061071a57610100808354040283529160200191610745565b820191906000526020600020905b81548152906001019060200180831161072857829003601f168201915b5050505050915060075490509091929394959697565b60016007819055508060069080519060200190610779929190610791565b5050565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106107d257805160ff1916838001178555610800565b82800160010185558215610800579182015b828111156107ff5782518255916020019190600101906107e4565b5b50905061080d9190610811565b5090565b61083391905b8082111561082f576000816000905550600101610817565b5090565b905600a165627a7a72305820194d22581d81d97445f3febf2511fde4ee6b074083b70b4ebe2bb0d70ec237790029";
	static {
		try {
			HttpService service = new HttpService(url);
			admin = Admin.build(service);
			web3j = Web3j.build(service);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static String addAccount(String password) {
		String address = null;
		try {
			Request<?, NewAccountIdentifier> request = admin.personalNewAccount(password);
			NewAccountIdentifier response = request.send();
			address = response.getAccountId();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return address;
	}
	
	public static String query() {
		String sysAddress = null;
		try {
			Request<?, EthAccounts> request = admin.ethAccounts();
			EthAccounts response = request.send();
			sysAddress = response.getAccounts().get(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sysAddress;
	}
	
	public static String getBalance(String address) {
		String money = null;
		try {
			Request<?, EthGetBalance> request = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST);
			EthGetBalance response = request.send();
			money = Convert.fromWei(response.getBalance().toString(), Convert.Unit.ETHER).toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return money;
	}
	
	public static Boolean unlock(String address,String password) {
		Boolean rs = false;
		try {
			Request<?, PersonalUnlockAccount> request = admin.personalUnlockAccount(address, password);
			PersonalUnlockAccount response = request.send();
			rs = response.getResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rs;
	}
	
	public static String transaction(String from,String to,String password,String amount) {
		String transactionHash = null;
		Boolean rs = unlock(from, password);
		if(rs) {
			try {
				BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
				Transaction transaction = Transaction.createEtherTransaction(from, null, null, null, to, value);
				Request<?, EthSendTransaction> request = web3j.ethSendTransaction(transaction);
				EthSendTransaction response = request.sendAsync().get();
				transactionHash = response.getResult();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return transactionHash;
	}
	
	public static String recharge(String userAddress,String amount) {
		String hash = null;
		String sysAddress = query();
		hash = transaction(sysAddress, userAddress, sysPassword, amount);
		if(hash!=null) {
			System.out.println("≥‰÷µ≥…π¶");
		}
		return hash;
	}
	
	public static String getTranscationHash(String goodsPrice,int goodsNum,String orderPrice,String buyerAddress,String sellerAddress,String payTime) {
		String transactionHash = null;
		Boolean rs = unlock(sellerAddress, sellerPassword);
		if(rs) {
			try {
				String code = FunctionEncoder.encodeConstructor(Arrays.asList(new Utf8String(goodsPrice),new Uint256(goodsNum),new Utf8String(orderPrice),new Utf8String(buyerAddress),new Utf8String(sellerAddress),new Utf8String(payTime)));
				Transaction transaction = Transaction.createContractTransaction(sellerAddress, null, null, bincode+code);
				Request<?, EthSendTransaction> request = web3j.ethSendTransaction(transaction);
				EthSendTransaction response = request.sendAsync().get();
				transactionHash = response.getResult();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return transactionHash;
	}
	
	public static String getOrderAddressByHash(String transctionHash) {
		String orderAddress = null;
		try {
			Request<?, EthGetTransactionReceipt> request = web3j.ethGetTransactionReceipt(transctionHash);
			EthGetTransactionReceipt response = request.send();
			orderAddress = response.getResult().getContractAddress();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return orderAddress;
	}
	
	public static String payOrder(String userAddress,String orderAddress,String password,String payTime) {
		String pay = null;
		Boolean rs = unlock(userAddress, password);
		if(rs) {
			try {
				Function function = new Function("payOrder", Arrays.asList(new Utf8String(payTime)), Arrays.asList());
			    String code = FunctionEncoder.encode(function);
			    Transaction transaction = Transaction.createFunctionCallTransaction(userAddress, null, null, Contract.GAS_LIMIT, orderAddress, code);
			    Request<?, EthSendTransaction> request = web3j.ethSendTransaction(transaction);
			    EthSendTransaction response = request.sendAsync().get();
			    pay = response.getResult();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return pay;
	}
	public static OrderDetail getOrderInfo(String userAddress,String orderAddress) {
		OrderDetail orderDetail = null;
		try {
			Function function = new Function("getOrderInfo", Arrays.asList(), Arrays.asList(
					new TypeReference<Utf8String>() {},
					new TypeReference<Uint256>() {},
					new TypeReference<Utf8String>() {},
					new TypeReference<Utf8String>() {},
					new TypeReference<Utf8String>() {},
					new TypeReference<Utf8String>() {},
					new TypeReference<Utf8String>() {},
					new TypeReference<Uint256>() {}
					));
			String code = FunctionEncoder.encode(function);
			Transaction transaction = Transaction.createEthCallTransaction(userAddress, orderAddress, code);
			Request<?, EthCall> request = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST);
			EthCall response = request.send();
			String result = response.getResult();
			List<Type> list = FunctionReturnDecoder.decode(result, function.getOutputParameters());
			orderDetail = new OrderDetail();
			orderDetail.setGoodsPrice(list.get(0).getValue().toString());
			orderDetail.setGoodsNum(list.get(1).getValue().toString());
			orderDetail.setOrderPrice(String.format("%.1f", Float.parseFloat(list.get(2).getValue().toString())));
			orderDetail.setBuyerAddress(list.get(3).getValue().toString());
			orderDetail.setSellerAddress(list.get(4).getValue().toString());
			orderDetail.setBuyTime(list.get(5).getValue().toString());
			orderDetail.setPayTime(list.get(6).getValue().toString());
			orderDetail.setStatus(list.get(7).getValue().toString());
			orderDetail.setOrderAddress(orderAddress);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return orderDetail;
	}
}
