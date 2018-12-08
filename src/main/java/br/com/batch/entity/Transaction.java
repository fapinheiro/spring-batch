package br.com.batch.entity;

//@Entity(name="transaction")
//@SequenceGenerator(sequenceName="seq_transaction", name = "seq_transaction")
public class Transaction {

	//@Id
	//@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_transaction")
	private Integer id;
	private String username;
	private String userid;
	private String transactionDate;
	private Double transactionAmount;
	private Double transactionIof;
	
	public Transaction() {}
	
	public Transaction(Integer id, String username, String userid, Double transactionAmount, Double transactionIof) {
		this.id = id;
		this.username = username;
		this.userid = userid;
		this.transactionAmount = transactionAmount;
		this.transactionIof = transactionIof;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Double getTransactionIof() {
		return transactionIof;
	}

	public void setTransactionIof(Double transactionIof) {
		this.transactionIof = transactionIof;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((transactionAmount == null) ? 0 : transactionAmount.hashCode());
		result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (transactionAmount == null) {
			if (other.transactionAmount != null)
				return false;
		} else if (!transactionAmount.equals(other.transactionAmount))
			return false;
		if (transactionDate == null) {
			if (other.transactionDate != null)
				return false;
		} else if (!transactionDate.equals(other.transactionDate))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", username=" + username + ", userid=" + userid + ", transactionDate="
				+ transactionDate + ", transactionAmount=" + transactionAmount + ", transactionIof=" + transactionIof
				+ "]";
	}
	
	
}

