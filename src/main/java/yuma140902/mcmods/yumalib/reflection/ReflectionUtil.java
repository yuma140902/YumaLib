package yuma140902.mcmods.yumalib.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cpw.mods.fml.relauncher.ReflectionHelper;

/**
 * 
 * @author yuma140902
 *
 */
public class ReflectionUtil {
	
	private static final Logger LOGGER = LogManager.getLogger("YumaLib-Reflection");
	
	/**
	 * Changes final field value <br>
	 * Example: <br>
	 * setFinalField(ItemFood.class, Items.baked_potato, 5, "healAmount", "field_77853_b");
	 * @param clazz       target class
	 * @param that        target instance
	 * @param newValue    new value to set
	 * @param fieldNames  target field name(s)
	 */
	public static void setFinalField(Class<?> clazz, Object that, Object newValue, String... fieldNames) {
		try {
			Field field = ReflectionHelper.findField(clazz, fieldNames);
			field.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			field.set(that, newValue);
		} catch (Exception e) {
			LOGGER.warn("Failed to tweak a property.");
			LOGGER.warn(e);
		}
	}
	
	/**
	 * Changes static final field value.
	 * See also: {@link #setFinalField}
	 * @param clazz
	 * @param newValue
	 * @param fieldNames
	 */
	public static void setStaticFinalField(Class<?> clazz, Object newValue, String... fieldNames) {
		setFinalField(clazz, null, newValue, fieldNames);
	}
}
