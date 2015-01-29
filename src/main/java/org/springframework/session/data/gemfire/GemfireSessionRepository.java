package org.springframework.session.data.gemfire;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;
import org.springframework.session.ExpiringSession;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;

public class GemfireSessionRepository implements
		SessionRepository<GemfireSessionRepository.GemfireSession> {

	private GemfireRepository repo;
	
	public GemfireSessionRepository(GemfireRepository repo) {
		this.repo = repo;
	}

	public GemfireSession createSession() {
		GemfireSession session = new GemfireSession();
		repo.save(session);
		return session;
	}

	public void save(GemfireSession session) {
		session.setLastAccessedTime(System.currentTimeMillis());
		repo.save(session);
	}

	public GemfireSession getSession(String id) {
		GemfireSession session = repo.findOne(id);
		if(session == null){
			return null;
		}
		session.setLastAccessedTime(System.currentTimeMillis());
		return session;
	}

	public void delete(String id) {
		GemfireSession session = repo.findOne(id);
		if(session != null){
			repo.delete(session);
		}
	}

	@Region("spring.session.sessions")
	public static final class GemfireSession implements ExpiringSession, Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6719898658776845883L;
		@Id
		private final String id = UUID.randomUUID().toString();
		public static final int DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS = 1800;

		private long creationTime = System.currentTimeMillis();
		private long lastAccessedTime = creationTime;

		private Map<String, Object> attributes = new HashMap<String, Object>();
		private int maxInactiveInterval = DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS;

		public String getId() {
			return id;
		}

		public Object getAttribute(String attributeName) {
			return attributes.get(attributeName);
		}

		public Set<String> getAttributeNames() {
			return attributes.keySet();
		}

		public void setAttribute(String attributeName, Object attributeValue) {
			if (attributeValue == null) {
				removeAttribute(attributeName);
			} else {
				attributes.put(attributeName, attributeValue);
			}
		}

		public void removeAttribute(String attributeName) {
			attributes.remove(attributeName);
		}

		public long getCreationTime() {
			return creationTime;
		}

		public long getLastAccessedTime() {
			return lastAccessedTime;
		}

		public void setMaxInactiveIntervalInSeconds(int interval) {
			this.maxInactiveInterval = interval;
		}

		public int getMaxInactiveIntervalInSeconds() {
			return maxInactiveInterval;
		}

		public boolean isExpired() {
			return isExpired(System.currentTimeMillis());
		}

		boolean isExpired(long now) {
			if (maxInactiveInterval < 0) {
				return false;
			}
			return now - TimeUnit.SECONDS.toMillis(maxInactiveInterval) >= lastAccessedTime;
		}
		
		public boolean equals(Object obj) {
	        return obj instanceof Session && id.equals(((Session) obj).getId());
	    }

	    public int hashCode() {
	        return id.hashCode();
	    }

		public void setLastAccessedTime(long lastAccessedTime) {
			this.lastAccessedTime = lastAccessedTime;
		}
	    

	}

}
