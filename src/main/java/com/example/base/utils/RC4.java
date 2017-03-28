package com.example.base.utils;

import java.io.UnsupportedEncodingException;

public class RC4 {

	public static String decry_RC4(byte[] data, String key) {
		if (data == null || key == null) {
			return null;
		}
		return asString(RC4Base(data, key));
	}

	public static String decry_RC4(String data, String key) {
		if (data == null || key == null) {
			return null;
		}
		String desString = null;
		try {
			desString = new String(RC4Base(HexString2Bytes(data), key), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return desString;
	}

	public static byte[] encry_RC4_byte(String data, String key) {
		if (data == null || key == null) {
			return null;
		}
		byte b_data[] = data.getBytes();
		return RC4Base(b_data, key);
	}

	public static String encry_RC4_string(String data, String key) {
		if (data == null || key == null) {
			return null;
		}
		return toHexString(asString(encry_RC4_byte(data, key)));
	}

	private static String asString(byte[] buf) {
		StringBuffer strbuf = new StringBuffer(buf.length);
		for (int i = 0; i < buf.length; i++) {
			strbuf.append((char) buf[i]);
		}
		return strbuf.toString();
	}

	private static byte[] initKey(String aKey) {
		byte[] b_key = aKey.getBytes();
		byte state[] = new byte[256];

		for (int i = 0; i < 256; i++) {
			state[i] = (byte) i;
		}
		int index1 = 0;
		int index2 = 0;
		if (b_key == null || b_key.length == 0) {
			return null;
		}
		for (int i = 0; i < 256; i++) {
			index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
			byte tmp = state[i];
			state[i] = state[index2];
			state[index2] = tmp;
			index1 = (index1 + 1) % b_key.length;
		}
		return state;
	}

	private static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch & 0xFF);
			if (s4.length() == 1) {
				s4 = '0' + s4;
			}
			str = str + s4;
		}
		return str;// 0x±íÊ¾Ê®Áù½øÖÆ
	}

	private static byte[] HexString2Bytes(String src) {
		int size = src.length();
		byte[] ret = new byte[size / 2];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < size / 2; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	private static byte uniteBytes(byte src0, byte src1) {
		char _b0 = (char) Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (char) (_b0 << 4);
		char _b1 = (char) Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	private static byte[] RC4Base(byte[] input, String mKkey) {
		int x = 0;
		int y = 0;
		byte key[] = initKey(mKkey);
		int xorIndex;
		byte[] result = new byte[input.length];

		for (int i = 0; i < input.length; i++) {
			x = (x + 1) & 0xff;
			y = ((key[x] & 0xff) + y) & 0xff;
			byte tmp = key[x];
			key[x] = key[y];
			key[y] = tmp;
			xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
			result[i] = (byte) (input[i] ^ key[xorIndex]);
		}
		return result;
	}

	public static void main(String[] args) {
		String inputStr = "11111奋斗奋斗";
		String str = encry_RC4_string(inputStr, "123456");
		System.out.println(str); // 7bda1b0947416cec0b151894c81c3c
		// System.out.println(decry_RC4(encry_RC4_string(inputStr, "123456"),
		// "123456"));
		System.out.println(decry_RC4(
				"aabf2839fd238f8fdf81f391539cc3cb2728c12b87427e85ff537f02857d2493cd646f10aa974598c30ce75798e44f0a30a5f1f0e1ab826570621143fb8f36cb9460c83c0bcaa2473e395e1b7c5fe357af7f773e5903f2209c7170a1958084ac29d3d4bd9f5a8a61b881851cf291fd2758a32a0121010111c0b02e88fc845813d8c04130c85103a2002b6564ac81722a348e30142e895bff36ce7b85127496bddf9355579f04760e283fbe9643a46d5c90020b991cb04c6d893c0f060f7752f75d80da59ee1889e0b8fca0acdd2c7365564df15f4e15a10636f50dc32a2992b6b76fa826c66335b2f1beb98ce88aeeefabb3d70b34a2a64f1bc7e2d9d5eb72902504fb56f0ca15c91971fb10fbadac4076b6b80ab6b49723e8f16d303ca7807ec7586f0aacaad1c484988687bd7ea6c0dde3c469d08be69f6426529aab3a566829d61e2ddd95c3f7d9009a310d156a5a748a9063ff82d54b4f0bc2793a48fb3b50550e1045174e10120eb863f8853fd737348b3bd526d4a45bac3ed0a26910b47d454e41500705e1fb089aa41a0228d68e19e4a394e11c2b68826d31b7004572d5dd09b232509d445fecc4b572a031649872f851c4104cde4ace38bb327383fcfa216cd803d26a82f5915bc68f92eaea4d922aadea6ad4987ba81f6c3927c78a5e4898da7f41c4501328458fd246899712c90fe7a5da98f6f7e3b1350295e63148e6b500506121876a1855a5a3c792dd2514a878534bcd369b96cfde168ccb66c1f5f18961c2af433497ca20c43482ccee5246f8e9de7a044d4035c7b0c89f507e1f3b6fefb5fa8ab4d9ec8fb991e0bf865fea3a4afb6c329135851bef39f0b90891ff8ecbbc446c2975cd3e2058748ca5a412fb6b5f92f3dd3051851dd3db87ba96d95d1bb90d0032fd7896c8519f3b1b3537fff19afef4cb26b1add9d0fe410b813247d08395f883821ea86d1a73715fe1642d246beb9fad85fa23252f36a42b4bd1dd5ff78c235c29f09d48b6269b2d085fb8f8efd0e68d2c9f56828e9b8fb42e453f4ed844559f0c04b3f369024a925cf457604d3a88666be3fe19cea5880ff7684439df5d82e2ea5cedcb39fe18537638994ca1c2215b6ab0f4d2c97197a2cafc41179eb00a137776deb485edcd9924e3361ecc2bd676ca5a21e3a9a703f309a60e2fe941adedfb484fac94bbee7312cf831d0cade2dde76237acf1cf7accf27bc6ff5c644917ac663513c3a6eb33b445158f12d212b235e70e6053dbd2bdcb1fa2f0c734d7a724497ccbf716d8581b8511cf17c997e3d936186050dc0644cd06a2b6c0b2ee34a548de73996e8f1e3d17a79c0d8f5488ecd3b64e0d019192ac8fc4c3ca0da7fd1192071d3fc2a4f479dd2198d67c4a06a9bd0f5da9d1dc922b733e61181d7b3b043d72660808c1f3a22c1864dbae7d9a6596d4d335d5d059fa799a36bda8576c9591437d92078af5e8964ea1dcd1e202b0ccb5898f4d0dcdd635735ec1244b743cbedc9bcce0f5eaaad39c8d7ae2cd3f4afcf8b7500549d3e4bc6287e6b71a88a0962c60e67eb16d0468e1ddb568e961afaedf7d02b1ab0936b9c2ecdb9631f6db6eead03923a558737bd973c87a2e34853f2549114395864d825f6576d20963c254e52f5d06c7759730d445537ac85fbf7edb5dfa7b0304bed62c3e031f9a0a342b5991221dd06b21b2007e6da0c2d572ac4d53cf7c72123c1c5b093c827b9b9042997c49132be1696c69b95435dc76ceb69738d97010624e770bc499906e34fedd5e5907def4c7128ac0513fc9d42b0daa638b9f080f67b8d36e8c5f40eb0b336a14f69d9de638896d065703fc3cb0ca470124dc1f438d030d04a71cc9cef49ee1f71ca3535076cd96c262302092977098b390c861a6849e6b3eb6ed65cc2df3b38d1e2a51c4920f91eaf3e90dd468764cfae4c54bce088951cef3b20bfb437db877998fa619f648efe4c83bf9323745c388a10927e1f66f1788f863f6dde758b382baf509ca79dfee30b5298a5ed70fc829b1f3edde25745b34b4c314a77050dec793c85f1ab746c413546ab1b68c5486349eec1f92ec4aee972c79d383cf53aa4134bfc98c18341145ffc73de87633553af2412e2b92a1e7b21bdfb5dbe11a6945f6308b7f17325216cdd00c2c1f9e45869a525c501e481a04a2398fd9b1aa725d2a6b293aa3c3037dd9d3851ca98ae9014f2d45f200b17771e748f544f4afd199d03f5425e499630dc543720da81db6c8adab767f4cb8e0965ffda0fdc246289430fcd43693f05b2f115fde8da15c6c22aa6bc9902d39db6d53da8b8a355f0a1170cdaf101c8f500049560d0be487c89a776f138cfb96e6663f82d2b0c6e1beff31583148d8f96d87fa929b96b8549dddc678e623f386a0db91f95c58aea07da68f4fcf6193af3cc81e0425bba4a2dabdab70e9fcdaefa2e165b27a0ec1352eaf6078921b041a96e123ec80e133e7d5219af4c44c04604dd9db2eda2c6f7a3126fd723405cd281932dd13fb9ea26fcb110d1bbd8060b7d045a2c5f786927a593ab123bbd9dd1035afbad96aa6410ea82348f7c41b3660e859f67b2498e9926573b93f212248028147a80903790a015f5c4a482c4336643aa361f5395cd9eec78d95c568ccd9d1f651b922d7067b5b0c4883b5084797604271ea54ca9ca628ffdb8d2c6cde168ff34e0b01db1798a3c9fb8d5b682374cc80cae11e3fb956a58d33476bb87db3ee6726f45a28a4bae318aa0cb524088718987e956f4cfbb77c47fca309a2d442b260fc32b45bf62af2465536c576d326aacc336960b9bd0c42ed8df47c004b89aede6df677bbd555b8db9e90cb75ed0722f294da9f61a10781260b263c19a77a78c87ba0f80b9852c03a720757f16fabe5cbd5814b2fc8f75f9ad2f6b0e70d0651e6dd28abf88fd380d687fa503b76e78d49ff662830eb0606fee4e85a6a25ecca23435896c1757cee0677b98cbe5d763c3511414e912aabfd87e5426cc3ae6249019e5774844b16184717692053d683171149d92827b0f2c397281eb5bfea8470d4d5bc70f29e756ecb4d4421669649c1e557c9870ec48677b065be92a172ce732549fccc86a82af6b6feffe98df24ce6d0d2a8bdef4b59fa6fcc3af8821316f9393678d65e8d517e213fbd4770fa0cfb92cd9a697efa0a779647c57d87999c804fab7c94dc057354d8eacfb9acbc240333732006fec828d78fabeb7f5e4e0f901efee7197e9c2397c9e1bc655927fba820624c03122eb6eaad7c509c5867ee70db08b664ba0941349e76a155cd53b9518992da2504679d6d2e40818febb8f719bdcba1faaf28065fcc5bb016e20965a095d001dbd70b5e41fdc7e89d5f4e1351c878249c14a849f6dbe83031d62926d5f94688ac58ab997e9aef8869bec09133562e92c96b8cd11638d02f9951cd48c7683d3c726f786146253b84c9947234363db9c9246792a3d58b6387913ab7f73a1f291eff0a7e8e522f0b353ad41d35cdcab9c24666c0899c3fc04aae69a101200b8052106bb15bc2864152fb012647a6e09f7452aeba63f4428e98c78d0544b01980c68e223eba64a1aee2d8758317b3441e4f7bf648a37460c3804219b0d7e1b4d1fe95bc3a667bd8b74f395be8e08953c001b63de5caf8879fb263a815b3da230288899ba0eff60b686c0db34794d156038623cfd13d5449b1526b03489535c43d3e993f746bfd13e066eb04bd4b793414008b371a19d1a5ad71dc0376c6a485711ee7f5bfee72a1cb45d88f105065593b544c562eec489589a59841df3629a79519f518160419b5ad9f0d7cfc8b72d4721e2f7c8ac2156ce270b4cc84d1ae83887d0c970e1858175800a48c2ca0af00a5dd17af06cac01459162dbf8779ee55bcadb8a580d2ff5d0c1cdb6a92099ed567001067c7fb3eb34ad744cf9354a29cc670c914ad5764bacf27c4dcd70d8fc38cd1b5c7bf9cae1e5318c2715e9be0efacb2171a3ac7cb9e66e1c1df394c5ea70c9017a0e2499abb0a20a589c9ee0c5fb7fddf67d043b5575d6fd5a1e6304a83af8a2005e41f2429215e5af00a8e2b5284ef51df2166ebc6dd2cf55018a82ea81687161c652d8fd119b858100c6fe95bed37002520968872489b8b97521fe0fff2c290bc2f6146160cbc6bb7d12542e9fede54c8bdefc60da85190ef36a84531364c339ca70dcab631dd07d6ce03fa8c952b81264038baf7611487229e1b298609e7cf3eb18ae2a4a7052cc0918764fdabe7d2e364993c4ec71d9f339cffa736f23c8cb09bcfe5629226c44fa929cf1ad8f6e231735348a5451223a4635ba355e90ddb5ea26552cd350332bcc74981d0aac916558c19987e350985e72ef17b770a9f93728e97b47970cbdf4ab5396da45a27591418be5dab318fa869c704793c1db53ccdeb57735fee09ab7128a252361bbc0201b8a2252f6a41d13a374dc927ea7296251bf86f53ebd7269b59813fee5e609221583a3d511b174f5d8fc827bf89cc1093b66a27148e8615a4b9e47d012815f6389a4a4f41723b0f9feb46d98dec0c1f4a675542b888f34d83ecddf84494fb6656709199aa49a80dcbfe0b92caf6b5845af39a720e8d0a4db1583637b91e22e929178a2fd0378c814da95c9087634b5d96af61f92b01d3c7a5134f385f3b6bd193b40aeba112dbb9f8d3a54380678c1afe060294554e4fd340154cc0c2baf298c71a10ceaf7867017d94102691a72b218ac3625f8df5bfa0d5318e9fb4029d115f5936e5de001509f1a0872f633d3d67a8ca9197256f919605ab3c8f8133ce88ef48c4a2a00cba2eb867237124bb290e439c16920263f5dbb1debb046e9d3b33cac936616f446ca6d7b20a28c422da23cd01382cf1fa6b3c2a5ff66876fd89b97e9e76c94e6fbee29da249fb4d5a2a76c99ea03a87b192ea2adddf0c8b65894751ad2024b7543b80cc1bc0da449a25cdd1fc9cf9f72df1d8ec2c0c214586051c6ee5e37ad9288b0d56f20bd8bbcdb4544a6303373dbc807af37d4e47853f80aa57e352be8c4ab5fbef65c600e82a0ef4e22fb0f8b19d7e5e9b9d371362664cecb370395b451042812552446a28e90e2cff5ece459e31739ba0df1059a782234895c389138a548e9978a645f5114a4ca1316bbba0b5663dfb6b6692e4471d3ec0a073c80a694eafa89434a54395cd836f2ccc1b04ba7998479acca24ebb32b646949ccc6772b87845690f348ae86ad99e202332766e1a909e488ff74855350419e4c3288188d89b5eb9ac270b17802857e0923af1b5e850945fb5da526495e8cf9501b5964807f7780a7f9f9b779fe93bd272632a4045f5019348ac5684b2b6392fdc1b5f66ce3b0b2fb76a07ea47a35b613bbb3c48af025e4357ebc6113e441130d6a73d3a29541c7bfa81c568a273bd98c08ee5d3bce70b224d357052261f624b056af412041df2aacff92dc8e8939f7d51b5a2ef43c348286cfaefc152dbc8d97d89089f20f3e50551db10623d30364865cf49d84ed2a8a98bc8be0ace2050182f55023e79c66d3b3b20790e786c57585ffc0628cefb6984e18da0ed501cebc21881a6e323905151c3c442d32d2d8446cdd45ca101239d6064b25be4fa24b01db596b597668ff7ef2ec66b8dc3307e554f564d8d2d8f573bec3506d8d66633207953716deca942bb302dac3c470dec9ef0c1e4d6eba5fc55ac9f3fb5e563826f1e37a2b775d30c5a735f7b3d3afca803be2b101bc9414acf161",
				"52e41ab0d3eee56b725e3e29999762a8")); // 52e41ab0d3eee56b725e3e29999762a8
	}

}
