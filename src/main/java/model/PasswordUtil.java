package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Gera o hash SHA-256 das senhas, para que elas não fiquem
 * gravadas em texto puro no banco de dados.
 */
public class PasswordUtil {

	public static String hash(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] bytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

			StringBuilder hex = new StringBuilder();
			for (byte b : bytes)
				hex.append(String.format("%02x", b));

			return hex.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 não disponível", e);
		}
	}

	public static boolean matches(String password, String hash) {
		return password != null && hash != null && hash(password).equals(hash);
	}
}
