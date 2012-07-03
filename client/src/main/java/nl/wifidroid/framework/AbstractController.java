package nl.wifidroid.framework;

import java.awt.Component;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class AbstractController {

	private final Logger logger;
	private Map<Class, List<DefaultEventListener>> eventListeners;
	private List<AbstractController> subControllers;
	private AbstractController parentController;
	private Component view;

	public AbstractController() {
		logger = Logger.getLogger(getClass());
		eventListeners = new HashMap<Class, List<DefaultEventListener>>();
		subControllers = new LinkedList<AbstractController>();
	}

	/**
	 * Register an event listener that is being executed when an event is
	 * intercepted by this controller.
	 * 
	 * @param eventClass
	 *            The actual event class this listeners is interested in.
	 * @param eventListener
	 *            The listener implementation.
	 */
	public void registerEventListener(Class eventClass,
			DefaultEventListener eventListener) {
		StringBuilder builder = new StringBuilder();
		builder.append("Registering listener: ");
		builder.append(eventListener);
		builder.append(" for event type: ");
		builder.append(eventClass.getName());
		logger().debug(builder.toString());

		List<DefaultEventListener> listenersForEvent = eventListeners
				.get(eventClass);
		if (listenersForEvent == null) {
			listenersForEvent = new ArrayList<DefaultEventListener>();
		}
		listenersForEvent.add(eventListener);
		eventListeners.put(eventClass, listenersForEvent);
	}

	/**
	 * Fire an event and pass it into the hierarchy of controllers.
	 * <p>
	 * The event is propagated only to the controller instance and its
	 * subcontrollers, not upwards in the hierarchy.
	 * 
	 * @param event
	 *            The event to be propagated.
	 */
	public void fireEvent(DefaultEvent event) {
		fireEvent(event, false);
	}

	/**
	 * Fire an event and pass it into the hierarchy of controllers.
	 * <p>
	 * The event is propagated to the controller instance, its subcontrollers,
	 * and upwards into the controller hierarchy. This operation effectively
	 * propagats the event to every controller in the whole hierarchy.
	 * 
	 * @param event
	 *            The event to be propagated.
	 */
	public void fireEventGlobal(DefaultEvent event) {
		fireEvent(event, true);
	}

	private void fireEvent(DefaultEvent event, boolean global) {
		if (event.isConsumed()) {
			return;
		}

		if (!event.isFiredInController(this)) {
			// notify every listener of this event type
			if (eventListeners.get(event.getClass()) != null) {
				for (DefaultEventListener eventListener : eventListeners
						.get(event.getClass())) {
					StringBuilder builder = new StringBuilder();
					builder.append("Event: ");
					builder.append(event.getClass().getName());
					builder.append(" with listener: ");
					builder.append(eventListener.getClass().getName());
					logger().debug(builder.toString());

					eventListener.handleEvent(event);
					if (event.isConsumed()) {
						break;
					}
				}
			}

			// mark the event as handled
			event.addFiredInController(this);

			// notify every listeners in the subcontroller of this event type
			if (!event.isConsumed()) {
				StringBuilder builder = new StringBuilder();
				builder.append("Passing event: ");
				builder.append(event.getClass().getName());
				builder.append(" DOWN in the controller hierarchy");
				logger().debug(builder.toString());
				for (AbstractController subController : subControllers) {
					subController.fireEvent(event, global);
				}
			}
		}

		// if this event is globally fired, traverse up in the hierarchy
		if (getParentController() != null
				&& !event.isFiredInController(getParentController())
				&& !event.isConsumed() && global) {
			StringBuilder builder = new StringBuilder();
			builder.append("Passing event: ");
			builder.append(event.getClass().getName());
			builder.append(" UP in the controller hierarchy");
			logger().debug(builder.toString());
			getParentController().fireEvent(event, global);
		}
	}

	public AbstractController getParentController() {
		return parentController;
	}

	public void setParentController(AbstractController parentController) {
		this.parentController = parentController;
	}

	public void addSubController(AbstractController subController) {
		synchronized (subControllers) {
			if (subControllers.add(subController)) {
				subController.setParentController(this);
			}
		}
	}

	public void removeSubController(AbstractController subController) {
		synchronized (subControllers) {
			if (subControllers.remove(subController)) {
				subController.setParentController(null);
			}
		}
	}

	public Component getView() {
		return view;
	}

	public void setView(Component view) {
		this.view = view;
	}

	public void dispose() {
		logger().debug("Disposing controller");

		Iterator<AbstractController> it = subControllers.iterator();
		while (it.hasNext()) {
			AbstractController subController = it.next();
			subController.dispose();
			it.remove();
		}

		if (eventListeners != null)
			eventListeners.clear();

		if (view instanceof Window) {
			Window w = (Window) view;
			w.dispose();
		}
	}

	protected Logger logger() {
		return logger;
	}
}
