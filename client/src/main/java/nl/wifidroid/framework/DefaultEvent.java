package nl.wifidroid.framework;

import java.util.HashSet;
import java.util.Set;

public class DefaultEvent {
	private boolean consumed;
	private final AbstractController source;
	private final Object payload;
	Set<AbstractController> firedInControllers = new HashSet<AbstractController>();

	public DefaultEvent(AbstractController source) {
		this(source, null);
	}

	public DefaultEvent(AbstractController source, Object payload) {
		this.source = source;
		this.payload = payload;
	}

	public AbstractController getSource() {
		return source;
	}

	public Object getPayload() {
		return payload;
	}

	public void addFiredInController(AbstractController seenController) {
		firedInControllers.add(seenController);
	}

	public boolean isFiredInController(AbstractController controller) {
		return firedInControllers.contains(controller);
	}

	/**
	 * consume this event to stop further processing of this event in the
	 * hierarchy
	 */
	public void consume() {
		consumed = true;
	}

	/**
	 * 
	 * @return returns true if a call to consume has been made
	 */
	public boolean isConsumed() {
		return consumed;
	}
}
