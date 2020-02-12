package com.portal.domain.userAgent;

/**
 * Interaface that gets string and returns extrancted version 
 * @author alexr
 */
interface VersionFetcher {
	Version version(String str);
}
