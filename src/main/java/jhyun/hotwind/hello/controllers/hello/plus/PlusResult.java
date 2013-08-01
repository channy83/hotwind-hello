package jhyun.hotwind.hello.controllers.hello.plus;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement
public class PlusResult implements Serializable {

	private static final long serialVersionUID = -8716033331126134742L;

	private PlusParams params;

	public PlusParams getParams() {
		return params;
	}

	public void setParams(PlusParams params) {
		this.params = params;
	}

	private int result;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public PlusResult() {
		super();
	}

	public PlusResult(PlusParams params, int result) {
		super();
		this.params = params;
		this.result = result;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
