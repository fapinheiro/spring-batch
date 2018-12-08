package br.com.batch.entity;

//@Entity(name="transaction")
//@SequenceGenerator(sequenceName="seq_transaction", name = "seq_transaction")
public class Statistic {

	//@Id
	//@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_transaction")
	private Integer id;
	private String username;
	private Double sumValue;

	
	public Statistic() {}
	
	public Statistic(Integer id, String username, Double sumValue) {
		this.id = id;
		this.username = username;
		this.sumValue = sumValue;
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

	public Double getSumValue() {
		return sumValue;
	}

	public void setSumValue(Double sumValue) {
		this.sumValue = sumValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sumValue == null) ? 0 : sumValue.hashCode());
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
		Statistic other = (Statistic) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sumValue == null) {
			if (other.sumValue != null)
				return false;
		} else if (!sumValue.equals(other.sumValue))
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
		return "Statistic [id=" + id + ", username=" + username + ", sumValue=" + sumValue + "]";
	}

	
}

