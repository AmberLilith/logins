package com.br.amber.logins.utils

import kotlin.random.Random

class Crypt{
    private val characters = mapOf(
        Pair("A", listOf("HÀá4", "ÚyÚf", "XÇë1", "oËoe", "Ù?¨ö", "IvvÔ", "jnbs", "bbIÚ", "KÒÄË", "íïÜd")),

        Pair("B", listOf("ntv.", "OS/Õ", "Lç}Ö", "`}4Z", "%4h9", "|Cîd", "7j³;", "Ñ4ïz", "A83*", "!ÛY3")),

        Pair("C", listOf("¨ÊH.", ";<(j", "~<fñ", ";fKk", "ÕW>Ê", "!û+,", "Ódù%", "q`j>", "HuHw", "WÑòî")),

        Pair("D", listOf("-:_P", "UD70", "ÙÉ:Á", "w%²D", "yÀÁp", "páÍ|", "`g#w", "£z(9", "¨a0!", "SÃÎz")),

        Pair("E", listOf("VyôG", "£8Ãw", "nLìÖ", "Â0âù", "5õ>d", "ÙCÎÖ", "9éÁH", "õUkÉ", ")Oí/", "+Í3à")),

        Pair("F", listOf("^Êéú", "JnZz", "fj²-", "¹%@á", "^ÇQ-", "ò)se", "t¹@j", "b#Z#", "DLÒO", "ÒúCÚ")),

        Pair("G", listOf("ö]¨q", "DsÌn", "!p?(", "èü/Î", "+AÒl", "R´Î!", "Jë#(", "ó[óÒ", "KÄ{ê", "zlvH")),

        Pair("H", listOf("Aw¢+", "£YçÖ", "Bg<Ù", "oÌV¢", "R>óç", "R7}:", "UJÌ>", "â0tH", "fâög", "AÈÑá")),

        Pair("I", listOf("yH}s", "¹Ã,q", "mezÕ", "ÂbZM", "cùbÁ", "mÑÖ4", "pn5x", "o.¨u", "BFïu", "9àHÙ")),

        Pair("J", listOf("èûUÓ", "VÃIx", "û´%e", "iîá7", "aLë.", "&i!)", "^ïôl", "m7I:", "î=xÕ", "úÒô6")),

        Pair("K", listOf("Vjdé", "mpaG", "À9Â¹", "Yìsx", "àÜD}", "²ñ3ò", "ÛB-õ", "=çàì", "ÄÖmC", "ùqgÂ")),

        Pair("L", listOf("I{Jú", "4áÎ7", "VÄWh", "CrËÙ", "¨õü1", "Z9ÑA", "=Â5Í", "´;â5", "j}B&", "ÙàÇb")),

        Pair("M", listOf("UõIJ", "Ú´Äë", "L.[<", "w.ÔQ", "xvq¨", "ªbUj", ".ÒÁÏ", "¹1sì", "Ve²o", "qÈ6î")),

        Pair("N", listOf("ªÍè8", "CïàÑ", "DK)ö", "b,0J", "7ïäö", "5Ol%", "¹Ûl¢", "¹Ë|~", "#áÖ-", "7%Sú")),

        Pair("O", listOf(")ñºC", "QÏOº", "=äÄh", "ÖEÛm", "mpDÎ", "È0tÁ", "n@q~", "P3ZN", "Úf.H", ".vîB")),

        Pair("P", listOf("Qj~e", "HÉ>F", ":[õà", "¢<ëÕ", "*mGä", "aB%h", "ëÃÌº", "}%ÔÑ", "gôÍ5", "YXÎ=")),

        Pair("Q", listOf("KÂuÛ", "S7ÑH", "¢0ìO", "Ëó/ô", "Ox²&", "|%x¨", "ÃÈéê", "(ç+ï", "sbQV", "~îEx")),

        Pair("R", listOf("Ù¹7 ", "³Ä?Ô", "êsWÁ", "Àï]{", "ïn¹|", "ÔÛ2Î", "O;mÉ", "éÕ¹É", ":R|-", "9úqÑ")),

        Pair("S", listOf("ó0`Ë", "MÓÖÌ", "zkÎÙ", "@î6&", "Aû1L", "lX@[", "UmÄT", "ÈÎÏÊ", "2eTc", "x=³D")),

        Pair("T", listOf("ïEuq", "A{E5", "ÏÊ6õ", "ì9éÚ", "x}ñ9", "¹FÈ²", "kôi7", ">&aÉ", "Ô³)õ", "GMCº")),

        Pair("U", listOf("²94L", "Ô}úí", "ó78ó", "õê<a", "àu^x", "UYíÂ", "tôî4", "0úà8", "| =§", "ÒÃ²x")),

        Pair("V", listOf("â¹dï", "{á},", "p¨Ró", "ÖRÓ]", "ÄïVË", "QÖbu", "+S~Ô", "3R?Ú", "<£ET", "QÊÑ£")),

        Pair("W", listOf("#Âí³", "¨_p¢", "áú5Ö", "gV+£", "Â9UH", "èõeÂ", " )de", " [P4", "ZvNì", "ë{Yô")),

        Pair("X", listOf("lõ0-", "UÑô!", " `yH", "ÒúÄH", "e,&6", ")wkú", "wMÏ*", "KC.Ê", "äÒÃí", "#~a.")),

        Pair("Y", listOf(" Kûm", "§ËÈé", "Ôê´.", "Y§t-", "mïù+", "+#,u", "XÁI§", "Y&Gf", "vWÑ´", "+óûH")),

        Pair("Z", listOf("ºâ6Ì", "SÕ#V", "³û.I", "ÔÊ-à", "£Jf8", "OüZx", "U%û¹", "é3`³", "V`=û", "bëñD")),

        Pair("Á", listOf("ªeüI", "uñFF", ">³òÊ", "p`rã", "frX@", "È{â-", "*vÍ|", "yï<`", "~ t²", "*7<ê")),

        Pair("À", listOf("çõë^", "h^ël", "{>ÎÏ", "x7 m", "0-ó3", "cË/)", "È +F", "ªÈ:)", "è+ÏÈ", "sQÓB")),

        Pair("Â", listOf("F:QV", ".Ìª5", "@çãí", "*!öi", "5<nW", "?ûTÄ", "K4JÀ", "qzc4", "KÌaë", "ZF?õ")),

        Pair("Ã", listOf("ïäÑy", "ÖSIm", "däcÃ", "z~Ê3", "S+oÎ", "6lÁ=", "5yÎg", "R>|¨", "G:óÙ", "6ÍEy")),

        Pair("Ä", listOf("CT!ó", "ñáRú", "sì@o", "(âº4", "ÉÈX.", "BÛ#W", "úñ)õ", "91õÛ", "m!`I", "^M{M")),

        Pair("É", listOf("v)H9", "ëJ>Í", "*#¨Î", "`¨äD", "ÎëÎé", "ZÃèE", "ïTjm", "`blx", ":ô{Ö", ">?Fw")),

        Pair("È", listOf("7OZj", "O)o[", "óëoz", "EZà0", "{6á³", "Éª¹ñ", "CxóÊ", "?E~õ", "Ó?PÔ", "8ªÊ|")),

        Pair("Ê", listOf("{pâö", "t²ïp", "Á§ib", "îºRó", "ÔÏnÁ", "ASiÜ", "g_*Â", "iÎ`Á", "P@S´", "bÈÃç")),

        Pair("Ë", listOf(")îêz", ".^CÉ", ".T+k", "^ZEÃ", "sv;8", "³vº~", "ü1û9", "i³ñ9", "^uÙ*", "<z5ô")),

        Pair("Í", listOf("ìö0Ê", "££ú´", "§ûsû", ",f~ñ", "=Ñ{¨", "qqº4", "á%ñ§", "ê!=Z", "]ãêh", "ÕòÜÑ")),

        Pair("Ì", listOf("ËWªL", "V,ÌD", "9ËxÓ", "YëâÎ", "X*8U", "uçLV", "wãpÉ", "&ºÍ£", "ÍÙNd", "£>óÔ")),

        Pair("Î", listOf("?uÃ)", "Ç`P]", "|PRg", ",Ón¢", "ögq+", "Ò8_ã", "2ëz§", "prk0", "K07:", "O/Ôì")),

        Pair("Ï", listOf("^FÊ§", "îfy_", "?¹ÔT", "?ç]i", "z5Jí", "Q/è]", "k/éw", "¹x<3", "Dwâm", "çh*É")),

        Pair("Ó", listOf("uªZì", "Ë]%k", "D[Uî", "dJiÌ", "X)JÒ", "VêPë", "TrõÜ", ":uOÇ", "uhnã", "pxöc")),

        Pair("Ò", listOf("hñv<", "azGé", "&óê|", "ÒÔt*", "6JËb", "ûqD3", ")Âó:", "²àBE", "*_IÔ", "%IFª")),

        Pair("Ô", listOf("Çb]Ç", "ÂÁHà", "lR§Ó", ")aï)", "é#ÊI", "ûaÌç", "Ònsù", "ù@W,", "ÒEâ0", "À¨îj")),

        Pair("Õ", listOf("Ó³UR", "lIÓ@", "Ì0rX", "Ã^³ª", "î!äü", "@üí´", ";M|J", "Û1pû", "sªÕg", "áByÚ")),

        Pair("Ö", listOf("¢/8Ê", "çsÒç", "vdÖ¹", "ñéÁç", "Ä|£Ã", ":ÃîÒ", "|JªÚ", "¹uHá", "(ò-&", "ÚóûÈ")),

        Pair("Ú", listOf("~¨Ò*", "ÛwfË", "£H7~", "Ä£SY", "yª²Y", "Â7~²", "úÒÎÌ", "eX£´", "[Ôbç", "jKíñ")),

        Pair("Ù", listOf("EkÁ|", "{8(I", "ÕHü&", "HuQd", "á_Úû", "îì>j", "èúfN", "Ë%Ìá", "ÈÖ?ñ", "*?%È")),

        Pair("Û", listOf("-ÀÉÛ", "8tñ´", "E{{Í", "}¹w9", "ÄZ5ô", "²Ãñ9", "Óëòo", "ê¢ô4", "AbjÍ", ".£3e")),

        Pair("Ü", listOf("úv¨=", "í[]ª", "úüyÀ", "0Ég4", "£fè9", "LG7(", "âQU<", ")ìbI", "ÚR#^", "ôâKi")),

        Pair("Ñ", listOf(";bdä", "b6Q&", "n@Rt", "ÈïÂ<", "ìhlè", ".D²ó", "lñfE", "-&Gû", "Ã;w/", "ìF#X")),

        Pair("a", listOf("WAE ", "%ó}x", ";fÈ0", "Fw@d", "ÈñçW", "&wjz", "+ÜZÜ", "Vî>Ì", "[Î,t", "ûô¨ª")),

        Pair("b", listOf("âò&|", "BO):", "=oSx", "àM5v", "dãÊà", "Ôá]U", "öDMz", "paB ", "ÖA#?", "öYÄú")),

        Pair("c", listOf(";ïmI", "ìüIT", "LúD^", "ÑÁBÖ", "ì:ëù", "%gwX", "ÀfºÏ", "Oúr}", "ÀÁ&ó", "euÇg")),

        Pair("d", listOf(";:ÒÒ", ":-fp", "2ÑVJ", "éV@q", "]WyZ", "08ör", "£ÉBÑ", "_aÉ6", "r8Ãb", "Á%,U")),

        Pair("e", listOf("Í-mÃ", "ñvíî", "cUçÎ", "spfÜ", "=,ÕV", "ô¨ ï", "òqÁ{", "ÏÏòC", "|8)B", "Òa>ñ")),

        Pair("f", listOf("H3%:", "y^Eê", "7Ü+g", "K1Ál", "^I2È", "òr]q", "sQÜ7", "(ó`õ", "~VsU", "oÁÇ²")),

        Pair("g", listOf("<ke_", "õ!a-", "aP=N", "#Elé", "[?2ä", "*Ä9n", ":Qûì", "Î>of", "^¢ä{", "iUWÉ")),

        Pair("h", listOf("ÈÁ¹ª", "%`Îú", "¢çgì", "ïDî-", "ürÎ´", "õ8öm", "Égáa", "h£cr", "V0&²", "È5º`")),

        Pair("i", listOf("xmÌ,", "S:;î", "}jÓt", "XÈ¢o", ".Eû ", "ï~¢Ñ", "90hã", "Ò2ÎE", "5P+L", "F`&t")),

        Pair("j", listOf("+F,j", "Öó@b", "H³Ç3", "~iió", "|!M!", "<Êñú", "ú¨1X", "éníh", "U)mÛ", "iw¢î")),

        Pair("k", listOf("sÖP[", "Uû§4", "§Âä3", "ÈÖ|â", "OÛ£§", "çá<â", "E.Öñ", "£IÂ¨", "S]{l", "DFiK")),

        Pair("l", listOf("E)99", "tïRË", "OëmÙ", "Ë5Ç ", "#Çºö", "w&C&", "Rº8l", "3ªE2", "úãÄ&", "rBÄg")),

        Pair("m", listOf("PéºP", "TP´Ï", "zñÌ*", "jûÖA", " à%N", "éÍ4V", "_ãín", "iR²3", "k%dq", "<[jh")),

        Pair("n", listOf("âä7û", "³¹ïÒ", "Jèî£", "èªÍa", "9Äxú", "Áx2B", "Ä63;", "Nª;Y", "N£1R", "(ÓRp")),

        Pair("o", listOf("áÈ¨ê", "ª?)|", "úàÀX", "v7Ïh", "Ó( Ò", "ÏÍùï", "7Ô r", "üo³o", ",JmÍ", "*~Áú")),

        Pair("p", listOf("ÎÎBü", "áPô0", ")²ä,", "³rXS", "/vHU", "ÕÈúÊ", "5+³É", "#u´4", "êbÂY", "7vÉy")),

        Pair("q", listOf("ÊRtÉ", "öé)ô", "ÍäA&", "ÊCùu", "ä,rg", "Gc{=", "g4OÙ", "T+bÚ", "1fÕu", "@âÃ5")),

        Pair("r", listOf("ÍäqÁ", "â0ÖÁ", "Ì ãX", "^!qh", "=Ù7?", "Ëtg2", "ÌÊñ=", "²ê:e", "2~nÌ", "xT³j")),

        Pair("s", listOf("]5o=", "ñYwá", "xSJU", ")hg7", ":îïk", "M1HI", "RiÜÃ", "tÁa|", "`Ä(Û", "Igë}")),

        Pair("t", listOf("]ÈüX", "R[Xë", "Òô:b", "ÚÄÔL", "t:Èu", "&ÓH@", "äÒGÒ", "Û^yÇ", ".Ñ`á", "ÓëÄA")),

        Pair("u", listOf("uöPV", "6!z?", "úxn.", "iqM2", "q î<", "aqX³", "ÓLîõ", "Ñq3ã", "ëo<ò", "h?|]")),

        Pair("v", listOf("¢L~Ò", "2jp|", "DÉí8", "írÓº", "¢5|ñ", "¨WNÁ", "¨v9B", "%dWí", "ég²ç", "0w6Í")),

        Pair("w", listOf("îÓÎg", "ÙÕWÁ", "rÉif", "è@;ü", "CIHK", "!pÍ~", "=@Bó", "Z6ÕB", ":ÏÏö", "zôêS")),

        Pair("x", listOf("coñÕ", "Óù]1", "ÚÜÂ;", "^X2w", "î;UÓ", "`À}À", "QêJs", "@.;O", "P²²b", "8§C/")),

        Pair("y", listOf("¹e@Z", "y{^f", "VGùC", "Î5~ì", "ÉÑ&s", "SË§õ", "0,¨B", "ãe;ù", "öáÙÖ", "úÇ|O")),

        Pair("z", listOf("vñ|(", "ktãd", "Ã<vf", "ÜEjº", "IÈjù", "SC.ì", "ä£B^", "¢ùÉs", "ìÓù*", ";¨ÌÎ")),

        Pair("á", listOf("H|-!", "Ï5XÕ", "Bë¢Û", "dBPH", "Xà=*", "S=ì6", "ÒYXÂ", "Iüsv", "îÜFk", "ãüXI")),

        Pair("à", listOf("èa&K", "èbêr", "¢HJè", "~]ót", "Jy.k", "ôâJo", "óûÖ!", "lp{3", "Ùo]=", "Ê+ªÄ")),

        Pair("â", listOf("8Hôö", "9JYi", "<ÓÏK", "%ò,{", "È45J", "ávè8", "oHªÂ", "2lò(", "0Úh¢", "^â 1")),

        Pair("ã", listOf(";ÉáÓ", "ë?ùO", "¨!Kõ", "õt9Õ", "ayëÌ", "ÒíñÑ", "¹ªWè", "Ì³4~", "ämJ=", "[y=Ü")),

        Pair("ä", listOf("ÎÛWè", "HYÒ(", "ÚTF-", "ûïõÎ", "Úúú£", "äpjX", "ÏëY(", "W^?P", ")4ëÓ", "19+o")),

        Pair("é", listOf("jzÂc", "Âïku", ",Ä#í", "oÏm]", "Ö5ÒÒ", ")Kwc", "zNÚ§", "fh/Ü", "|2MG", "ÚúÉi")),

        Pair("è", listOf("sòYm", ">ÔFÃ", "6éhJ", "V,!S", "SN1Ô", "ªCôY", "¨ñ+b", "NJÒ<", "LSV;", "ô2/a")),

        Pair("ê", listOf("NÖãc", "ÒZb.", "ÃjÙt", "ÁPíì", "è1c~", "Z#b_", "Ãmé~", "Z];O", "õJJÊ", "LÃÔÈ")),

        Pair("ë", listOf("àl_F", "Ó¨Îí", "Û¹WÏ", "c¹ªÓ", "q£jK", "[XGS", ";Õ{(", "?âLq", ".´.è", "@cT_")),

        Pair("í", listOf("`èì0", "ËPQi", "ÄÄlv", "NÒCZ", "èZkÂ", "£îâ!", "8v*_", "vÏ`è", ".ÀV1", "%ÇÚa")),

        Pair("ì", listOf("7/N£", "ksp]", "úîV²", "_[õ¢", "G+F^", "î1pÀ", "À JM", "8ô~Ô", "ÊmpÓ", "ò_]ü")),

        Pair("î", listOf("ÔÜW¹", "8`UÊ", "Ù=6(", "cÈZò", "M[|Q", "ò;¹Á", "iö)I", "oöèÁ", "ÖOñä", "I}NY")),

        Pair("ï", listOf("§ÌÜ(", "R#)ù", "ÜÚ§]", "~Û¨K", "OtG¹", "aâàÍ", "ícãQ", "Êc ê", "êT²V", "uÃÔ|")),

        Pair("ó", listOf("í{%4", "M/B8", ",o]E", "È+Vd", "ñê7à", "XznN", "/18E", "LénS", "Y8Ys", "Yz}²")),

        Pair("ò", listOf("²ó>ö", "ÜYhÂ", "ÜÂ*8", "Q>wè", "£Úïé", "ÓWÊP", "ÔCí2", "XáwË", "%Xid", "ÌXí´")),

        Pair("ô", listOf("ù`ª6", "=o-Ú", "}U]â", "ªa<î", "öRCc", "lg]I", "^_c8", "#<bü", "VÔnz", "ãPUA")),

        Pair("õ", listOf("(ÀvY", "vÂÕë", "ÁÜëg", "6Èüj", "5u= ", ">qC{", "ÚbqÛ", "R£2i", "ÒW~ó", "ñ³Ó)")),

        Pair("ö", listOf("uÀX_", "ôlñ£", "<Â(g", "?IX<", "âèâ_", "|ÃÕc", "s0ä}", "ïnÄy", "]OhÏ", "JWû§")),

        Pair("ú", listOf("¨Xù§", "ñ:õx", "K5ì[", "=ë¨h", "Í2rÚ", "iARN", "eD;Ö", "Õ_7S", "5v1s", "^Aò]")),

        Pair("ù", listOf("g!áÊ", "ctdÚ", "%!Gí", "5ópù", "cNÜu", "!*Ûa", "F7¢/", "ÀrhJ", "EzPq", "^YGü")),

        Pair("û", listOf("/@³Õ", "ãE`M", "(ä)Z", "Ä[aM", "tn¨Ù", "DR3ï", "Ç)xÎ", "=pMO", "ÒH+Ì", " 4QÄ")),

        Pair("ü", listOf("Ù¨+q", "C2?2", "9=yÖ", "?£3Õ", "}M³ó", "<úÔg", "].9/", "É8>ì", "Î?¨ò", "-VÖÂ")),

        Pair("ñ", listOf("ë4ÓÄ", "-3¹Â", "T[Èã", "Ú*%Ä", "vö0ê", "Ç/^g", "b¢7B", "7t1{", "Ou<R", "ëzê<")),

        Pair("1", listOf("ó[?u", "CGÄP", "0}Hj", "BïW]", "ÂU7Ö", "îx¨¹", "hN<m", "²-Üû", ".¢Ê&", "ÄÕsW")),

        Pair("2", listOf("ÈìQû", "³ÌrI", "õPìZ", "è~`/", "ÖìXû", "ÈÙ%y", "ìEèÃ", "À23ë", "Öab³", "oÄÊ`")),

        Pair("3", listOf("Z`>N", "oËÑÃ", "tª¢ª", "¢ù(e", "[ÛöX", "´Ú-)", "@4É*", "Ñò5y", "ÈWh5", "}5Ö³")),

        Pair("4", listOf("pºúo", "Ùaë@", "²%|I", "{nªp", "ütô ", "L,(I", "ÉhkR", "u1â9", "Àd1í", "[ZA=")),

        Pair("5", listOf("ttTë", " CVª", "mf#9", "Õ_éÚ", "ÔÖÊü", "ïIî[", "Ñ §ê", "[4?8", "á]îù", "léFÏ")),

        Pair("6", listOf("2u4>", "ûÛ³(", "ópeQ", "v/Ûc", ")sGh", "Rw&}", "ÜGxÙ", "ÏÂd8", "ÁÍÇ}", "ÃàÒD")),

        Pair("7", listOf("#^Ê=", "nùSq", "züvF", "JZùò", "_ÄÄ ", "èÔ!R", "¹Ëpd", "ká¢Ä", "Í#,Y", "È´ÊZ")),

        Pair("8", listOf("Z%6N", ":ªJV", "~~ä0", "]´íQ", "õK²Ç", "õÜwO", "bD8,", "_Eç>", "rf%´", "oÇGt")),

        Pair("9", listOf("IxÊá", "?úôÑ", "Ã5ãH", "£nG4", "p6ë[", "xt)§", "ó;îX", "â/2Ë", "gÓ,?", "%o:Â")),

        Pair("0", listOf("h>:f", "LRÖÜ", "à1Ìl", "ÒÈ0ù", "ûZ5ù", "Ef´Ó", "Ê£KR", "4<t¨", ".gyö", "zñ!´")),

        Pair("!", listOf("ä7Mp", "£L{õ", "5çËÓ", "õEï£", "#/Ä@", "ÇzÈ²", "~^oê", "x,VË", "Ebuê", "¨]aÓ")),

        Pair("¹", listOf("ÎR´Â", "ÄÊ£O", "Äq@Q", "ãAoñ", "é%`=", "SÌ+f", "BJ+#", "~tI_", ":%an", "ò´LÁ")),

        Pair("@", listOf("vN#s", "ëÂ¹B", "ËÜúF", "È2^e", "§}Ìu", "û¹Ö-", ";,zÏ", "è*ïC", "8ñÕ@", "^,ªt")),

        Pair("²", listOf("Ë%6o", ";ÏÜF", "bnúá", "sr,ä", "ÌÛD6", "3wëâ", "*ÑcM", "À6më", "u`1o", "3N²9")),

        Pair("³", listOf("Û:MÁ", "ÊUìj", "ëJUI", "%Gvk", "E~=Ö", "´ï(F", ",féJ", "f}í)", "MHj§", "Ñ§jö")),

        Pair("#", listOf("<j9x", "NñtÂ", "FeOq", "çÙxè", "Ò§ôp", "O>}2", "ºH,é", "ÃszC", "éXët", "ÊËú!")),

        Pair("£", listOf("K1¢z", "cV´Ö", "ÑaYª", "£szª", "xiqs", "Ò.1n", "û;jÊ", "^;.²", "hù%Ê", "cP>8")),

        Pair("%", listOf("/GâÃ", "ÇEM/", "£O]h", "ã}öd", "hv(î", "bApÒ", "ÍòHw", "J¨mo", "};²t", "ê~ãï")),

        Pair("¢", listOf("]UL6", "süò~", "Ìñ³k", "ÑÃ`/", ",,´¢", "¢²îG", "cÏ/;", "îÙÁ7", "û5ví", "íeÁp")),

        Pair("¨", listOf("7K`F", "ª~Ä<", "P¹ÖS", "²xïÁ", "ìF¨u", "³pL}", "~èXd", "2o4ª", "ô24ë", "LÙh[")),

        Pair("&", listOf("ºë`_", "Eó2s", "3üÖ³", "gU{ä", "¨aÖV", "Fkãh", "]Ïqï", "^EÓï", "&îòÜ", "óJÒª")),

        Pair("*", listOf("Ö?+ª", "ªÄÉ@", "U´~Ï", "õó¢#", "9É`ã", "²Á¢~", "A/ÀÁ", "õÉ<ä", "ª¹r<", "4kbõ")),

        Pair("(", listOf("b8,W", "gHÃ(", ")e1Z", "9e:é", "î,Ê_", "zBü>", "Rm*ó", "^p96", "{U?ï", "/J1#")),

        Pair(")", listOf("fÒNL", "FbÏa", "ÉÃ%Ö", "Q+eÓ", "w@dù", "u{a1", "%^HÁ", "sÖzv", "{?|=", "âñ}<")),

        Pair("_", listOf("óuä ", "ÑÉRX", "rJ(ª", "sPah", "ûÒDC", "ÄeÙ²", "@!x3", "Ñje.", "Qü1V", "ãÚÃS")),

        Pair("-", listOf("7ÃM`", "<:Û¹", "ú;ZÄ", "mP!É", "úÃP2", "BV=<", "ã_ñÖ", "ë´lÃ", "sH¹¨", "0ïXO")),

        Pair("=", listOf("/ätc", "^m{V", "|öúÑ", "ÄzxÔ", "UeÈs", "Ù]N_", "íÃu´", "YîÈt", "¨ÇBs", "º/TÄ")),

        Pair("+", listOf(",2EM", "6ÓRù", "¢ Lò", "³4Ì2", "ûxÓÀ", "àªÇú", "ãàâJ", "RH;K", "ëZF6", "T+LF")),

        Pair("§", listOf("7ü@.", "eka0", "úUÒÉ", "3s2t", "ú7-Y", "#¢BL", "ùa9Î", ")jsè", "_î í", "ÃÔ´º")),

        Pair("[", listOf("gÜh£", "dÌï¹", "Ì¢bÒ", "t§l<", "èÛÀ^", "U§^p", "Ö:ÀM", "Ôû¢Û", ".*¨c", "n¹Úã")),

        Pair("]", listOf("cjpÛ", "äJèL", "OînT", "eB¨â", "2âÎÀ", "^¢ÊY", "Jé3%", "r£ÉÇ", "<|=+", "²t2è")),

        Pair("{", listOf("%ïVQ", "&Ò/}", "aÏr(", "a1 #", "ÉÕh!", "EkdH", "2=90", "V8ég", "Pò`¢", "d0nÉ")),

        Pair("}", listOf("}nb:", "Ã<ïp", "Öuê7", "Ú[KH", "hfHá", "Ó^=í", "FÁ;q", "qî´u", "_.À/", "öxÚN")),

        Pair("ª", listOf("óB@x", "RÄíÌ", "qw.§", "¨%T(", "¢uè(", "döÄo", "/î^<", "O7&ñ", "%!Ë4", "7e+Ç")),

        Pair("º", listOf("KëeÖ", ")übi", ",úO¢", "Ydu5", "u(FU", "BÀmm", "bALô", "ZrPã", "V8nô", "Z=IZ")),

        Pair("~", listOf("7r>Q", "O,Èë", "§§/§", "eNüÒ", "@ÄqÜ", "fAHJ", "ó+eÇ", "î_Fn", "BAdÜ", "³F%,")),

        Pair("^", listOf("p£&Î", "v2¢6", "óÎºª", "Ã?çs", "R8dp", "|E.r", "Ä{7E", "#|H%", "JÊmÀ", "û7Fa")),

        Pair("´", listOf("yÄ{_", "á_3t", "ù~ó[", "/QMA", "EGñ¢", "o/Bá", "ä|§E", "cwIé", "Î:`Õ", "¢+ã=")),

        Pair("`", listOf("y2i[", "íÑ4Î", "e&¨J", "Èºua", "//£ë", "§|ä}", "ÜÕç&", ".VHú", "YÔÊ9", "w#xÑ")),

        Pair(":", listOf("ºZpY", "wë è", "é5LK", "mÊ?8", "u-BS", "P+{8", "@úÍQ", "´0_Ç", "05qP", "|6ïk")),

        Pair(";", listOf("ñT7K", "ûÒôî", "3HÏY", "{é/4", "p[Á³", "ÑºÊÌ", "|OIÔ", "vWPÒ", "0ef*", "%ZhD")),

        Pair(".", listOf("LÊK%", "v£_,", "´Ââä", "à/?:", "Ç~Uä", "0eçÈ", "ñ%îy", "Û*hH", "ûó?I", "ÙªêP")),

        Pair(">", listOf("öç_ü", "tyJA", "3:h&", "ü~êP", "1ó(c", "ÌçXL", "n?Õî", "Á/Üá", "=P]#", "û¢LD")),

        Pair("<", listOf("àt¹b", "ë@oS", ",Ë2K", "OcÊ!", "(;@b", "t;^á", "ÉÊ,§", "DKqw", "9Föa", "<<Aj")),

        Pair(",", listOf("ÎÇhX", "é3oD", "eÓBP", "&ÏLN", "KlCÌ", "<Ì)ú", "m7tW", ")Íáî", "dÙûà", "&Ûz³")),

        Pair("?", listOf("Ú^3x", "??Mr", "2l>w", "£xKO", ">ÉÀf", "@üÍÑ", "i.ÍJ", "Mçàz", "0ùÓà", "PeCF")),

        Pair("/", listOf("xçN@", "ìºíj", "zÈ)à", "ài[â", "èÔÌª", "oR²M", "&ûÛ ", "4Á¨û", ",Còç", "ÓùÏF")),

        Pair("|", listOf("AP7á", "ú6¢M", "Â2ÂM", "fB£Ì", "Ùõ¢Ô", ">Äëò", "rQ#@", "del=", "ãÛ£M", "Ëu/j")),

        Pair("ç", listOf("I7iô", "<Ímª", ";ÀD#", "ãOÄ@", "éRÒ&", "çWYj", "ÜÈdè", "Õ>ÜH", "èÉ5h", " kHk")),

        Pair("Ç", listOf("YMïí", "Úop¹", "éù5*", "&^KÍ", "_xe>", "A]3f", "%ÔÀû", "kâ,S", "³Ú³`", "w8d`")),

        Pair(" ", listOf("Éë72", "úÔ:M", "k8Ít", "&ËÂñ", "{ÓMÕ", "%fíÌ", "ìt-u", "ê Ã3", "Ó´fÏ", "Î6ÙL"))
    )

    private val aggregators = listOf("ãHK;", "lÙÚ§", "Ü5e²", "ëï^Á", "Âj {", "Û0Pò", "Éàmô", "óõ£Y", "Úfây", "êMv¢")

    fun encrypt(data: String, key: String, aggregator: Int): String {
        val dataToArray = data.toCharArray()
        val encryptedData = StringBuilder()
        val keyToStringList = encryptKey(key)

        if(aggregator >= 0 && aggregator < aggregators.size){
            if(data != ""){
                if(key != "" && key.length > 5){
                    for (character in dataToArray) {
                        val listRefToLetter = characters[character.toString()]
                        val randomIndex = Random.nextInt(listRefToLetter!!.size)
                        encryptedData.append(listRefToLetter[randomIndex])
                    }
                    encryptedData.insert(0,aggregators[aggregator])

                    keyToStringList.reverse()
                    keyToStringList.forEachIndexed{ _, setOfCharacters ->
                        encryptedData.insert(0, setOfCharacters)
                    }
                }else{
                    throw Exception("chave de segurança inválida!")
                }
            }else{
                throw Exception("Os dados para criptografia não podem ser vazios!")
            }
        }else{
            throw Exception("O aggregrator ter valor de 0 a ${aggregators.size}")
        }

        return encryptedData.toString()
    }

    fun decrypt(wholeEncrytedData: String, key: String, aggregator: Int): String {
        val decryptedData = StringBuilder()
        var decryptedKey = ""

        if(aggregator >= 0 && aggregator < aggregators.size){
            if(isDataValidEncrytedValue(wholeEncrytedData, aggregator)){
                if(wholeEncrytedData != ""){
                    val (encrytedKey, encrytedData) = wholeEncrytedData.split(aggregators[aggregator])

                    println("Dados: $encrytedData separados da key $encrytedKey")

                    decryptedKey = decryptKey(encrytedKey.chunked(4))

                    if(key == decryptedKey){
                        encrytedData.chunked(4).forEach { item ->
                            decryptedData.append(findLetter(characters, item))
                        }
                    }else{
                        throw Exception("Chave incorreta!")
                    }
                }else{
                    throw Exception("Os dados para descriptografar não podem ser vazios!")
                }
            }else{
                throw Exception("Os dados não tem uma criptografia válida!")
            }
        }else{
            throw Exception("O aggregrator ter valor de 0 a ${aggregators.size}")
        }

        return decryptedData.toString()
    }

    private fun decryptKey(setOfCharactersKey: List<String>): String{
        val decryptedKey = StringBuilder()
        setOfCharactersKey.forEach { item ->
            decryptedKey.append(findLetter(characters, item))
        }
        return decryptedKey.toString()
    }

    private fun encryptKey(key: String): MutableList<String>{
        val keyToArray = key.toCharArray()
        val encryptedKey = mutableListOf<String>()
        for (character in keyToArray) {
            val setRefToLetter = characters[character.toString()]
            val randomIndex = Random.nextInt(setRefToLetter!!.size)
            encryptedKey.add(setRefToLetter[randomIndex])
        }
        return encryptedKey
    }

    private fun isDataValidEncrytedValue(data: String, iv: Int): Boolean{
        val dataWithIv = data.chunked(4).toMutableList()
        dataWithIv.remove(aggregators[iv])
        for(item in dataWithIv){
            if(!characters.any { (_, lista) -> item in lista }){
                return false
            }
        }
        return true
    }



    private fun findLetter(mapa: Map<String, List<String>>, valorProcurado: String): String? {
        // Usando find para encontrar a primeira chave correspondente ao valor
        return mapa.entries.find { it.value.contains(valorProcurado) }?.key
    }
}