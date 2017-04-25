(ns astrogue.renderer)

(def shape-player (str "m 47.600815,17.9372 c -0.101451,3.253664 -1.138592,6.561822 -3.11362,9.163908 -1.279685,1.853662 -3.276461,2.846127 -5.374747,3.477753 -2.700786,0.01979 -2.498849,2.445447 -0.497063,3.470764 1.707458,1.574715 3.484555,3.116172 4.264296,5.322094 1.588692,0.670217 3.85307,0.439932 3.968784,2.906434 0.855664,2.224823 -0.438512,4.805498 -3.008203,4.690827 -2.048127,0.83762 -5.697947,-0.534555 -3.784126,-3.107062 1.007237,-2.047551 -0.09776,-5.44072 -2.207936,-6.128484 -1.371878,1.648788 -0.131695,4.627584 -2.021309,6.138297 -1.935002,1.689367 -4.701629,1.551055 -7.104386,2.081787 -3.111878,0.538058 -6.332204,0.329187 -9.421971,-0.234162 -2.22038,-0.621567 -3.898906,-2.277037 -4.40266,-4.556296 -1.229254,-1.458318 0.264575,-5.157694 -1.800045,-5.237647 -1.400509,1.353586 -4.9444857,3.274123 -3.7017365,5.365891 1.9801585,1.282195 0.4544427,3.883022 -1.4917357,4.076555 C 5.8721687,45.985931 2.7740382,45.163323 3.1144649,42.505554 3.2377274,40.154238 6.4063782,40.421306 7.511649,38.669516 9.2707156,37.22946 10.070145,35.022894 12.029343,33.7719 13.36526,32.972203 14.708891,30.922876 12.313314,30.523618 10.268027,30.109676 8.6952832,28.760833 7.5903581,27.028988 5.8232508,24.346798 4.1094493,21.369329 4.3415885,18.034777 4.1634027,14.952729 4.7752884,11.717559 6.7186227,9.2534502 8.2636834,7.1182493 9.9772633,4.983188 12.164685,3.4746542 14.08339,2.180847 16.206562,1.1376638 18.564448,1.0936975 c 4.605871,-0.27984518 9.263207,-0.15289 13.765699,0.9421245 2.948122,0.5100634 5.998363,1.0466985 8.441211,2.8929791 2.683537,1.6286989 5.077293,3.8942372 6.205693,6.8836709 1.175109,1.861379 0.648396,4.066184 0.623764,6.124728 z M 44.105177,14.013371 C 43.348639,11.490444 41.262229,9.6909516 39.505615,7.8263976 "
                       "37.685837,6.0864351 35.25807,5.0875141 32.867241,4.3876329 27.614352,3.5810626 22.154036,2.9604578 16.910637,4.1380584 13.614559,5.3787133 11.235375,8.0866136 8.809322,10.515863 c -2.0672711,2.89524 -1.8410826,6.797411 -1.0527967,10.100288 0.8465129,2.401042 1.9724151,4.95842 4.0481337,6.543202 3.565168,1.879969 7.676001,1.894265 11.555954,2.5905 4.931255,0.431629 9.865778,-0.344222 14.719429,-1.126748 2.593015,-0.400226 4.181973,-2.318224 5.116866,-4.624421 0.978966,-1.899253 1.457725,-3.994831 1.294138,-6.132303 -0.08662,-1.269393 0.26052,-2.686288 -0.385869,-3.85301 z m -7.339788,7.994867 c -0.597369,2.447012 -2.423195,5.166531 -5.281491,3.637545 -2.054505,-1.044931 -1.401924,-3.900849 -1.68668,-5.81626 -0.160449,-2.160977 1.030458,-5.115095 3.64184,-4.81238 2.339732,-0.09235 3.802619,2.279869 3.755648,4.412802 0.03554,0.87567 -0.09004,1.766914 -0.429317,2.578293 z M 20.233457,18.669986 c -0.49354,2.347536 -1.046464,5.357299 -3.344748,6.521426 -2.021292,0.255355 -4.183142,-0.737648 -4.973662,-2.686787 -1.563768,-3.857299 -1.482298,-8.597096 0.936494,-12.089433 1.332814,-1.6176234 4.542004,-2.6929956 6.170122,-1.1113953 1.14528,2.7273103 1.324936,5.8157273 1.289432,8.7537273 l -0.03247,0.33257 z m 13.84477,17.920335 c -0.84589,-2.320982) -1.918269,-5.408544 -5.038398,-4.416474 -3.217502,0.300888 -6.442401,-0.116608 -9.656259,-0.06629 -2.322603,0.575585 -2.753195,3.711767 -2.828868,5.781195 -0.129929,2.68328 1.86704,4.943397 4.302108,5.789308 1.741996,0.208697 3.577824,0.0045 5.350132,0.05222 2.273843,-0.13775 4.680617,0.01865 6.8156,-0.887084 1.474165,-1.667451 1.303892,-4.18119 1.055685,-6.252875 z"))

(def shape-mushroom (str "m 20.4367,0.80698721 c -1.128518,0.001697 -2.252198,0.0457868 -3.360014,0.10784842 l -0.0019,0 -0.0019,0 c -0.840034,0.0836944 -1.579532,0.45413257 -2.301989,0.84790557 -2.066062,1.0159682 -2.091167,1.6330234 0.05897,0.2677597 0.717435,-0.3910428 1.060439,-0.6284199 1.857579,-0.7087123 0.0013,0 2.354404,-0.1382895 2.355653,-0.1382895 4.417806,-0.24730776 8.340395,-0.76880948 12.443132,1.0108943 1.174828,1.0423996 0.69386,1.0126246 2.142613,1.5332433 1.454064,0.5225357 3.621043,2.0803669 4.472488,3.4312034 l 0.152499,-0.094835 C 37.369367,5.6607834 34.11801,2.726573 32.656329,2.201307 28.228293,0.11401338 25.230743,0.82094637 20.436669,0.80697024 z M 21.090355,45.521962 c -2.523524,-1.718741 -5.444044,-3.523928 -5.564372,-4.437899 -0.467627,-1.327955 -0.831109,-2.571294 -1.21347,-3.857409 -0.279016,-1.998497 0.524227,-3.977402 1.213465,-5.832368 0.651909,-2.565286 1.346585,-5.113524 2.165401,-7.633362 -0.166761,-1.831785 -2.701368,-0.511076 -3.7188,-1.023472 C 12.658634,22.378439 10.85499,22.392922 9.9699904,21.191646 8.7815844,19.894554 7.8330188,19.301905 6.5042418,18.204216 4.8352373,16.736163 5.5008458,14.013911 5.6416335,11.99257 5.896034,10.182593 7.083631,8.6876496 7.9805257,7.1573439 8.9530934,5.9775775 9.7353564,4.6489721 11.098531,3.8360115 11.939593,3.2160656 13.878073,2.0601589 12.45002,3.6193524 13.752176,5.3069104 10.021187,3.9493753 9.6408894,5.5873857 8.2480118,7.0918427 7.460275,9.1271732 6.7346167,11.056773 c -0.4634519,0.920707 1.0666818,1.091704 -0.083976,1.109376 -0.1977677,0.650591 0.561979,1.270097 0.2486342,1.971626 2.1310105,-0.214885 -1.1237204,2.60508 0.088468,2.492788 0.093478,-1.045964 2.3694995,-2.333184 1.4574815,-0.477771 -1.4871792,0.982797 -0.1867411,3.118196 0.833059,1.342926 1.6047726,-1.522664 -0.742051,3.323975 1.7587756,1.839153 0.512723,-1.078762 1.655259,-1.409791 1.009489,0.161118 0.411938,1.799124 1.970615,0.03723 2.372117,-0.846803 0.701547,0.217378 -0.424469,3.147722 1.355523,1.77102 0.291205,-1.061512 1.558434,-2.410003 0.971612,-0.346344 -0.378422,2.049766 1.883188,0.280356 2.122738,-0.739278 1.442147,-0.293114 -1.750821,3.141123 0.554856,2.943362 1.157703,0.872223 2.300134,-3.124403 2.529211,-1.467553 -0.913584,0.788883 -0.717555,2.788501 0.47616,1.275303 1.643402,-1.87286 -0.145603,1.74046 1.82318,0.998795 0.927389,0.283249 1.5463,-2.881317 1.950784,-1.143968 -0.04995,1.609531 -1.069175,2.276241 -2.575978,1.714059 -1.378819,0.747227 -2.491282,-0.520942 -3.849735,0.288989 -1.182236,-1.883992 -2.517921,2.538873 -1.278457,3.201071 0.349105,1.090099 2.077934,-0.160708 2.352519,1.496343 0.839199,0.585727 1.559342,-2.057965 2.369392,-0.155289 0.596891,1.398871 1.357941,2.020275 2.226927,0.549008 0.962452,0.700609 -1.070926,3.448839 -1.7696,1.298991 -1.004687,-1.429389 -1.589046,0.16888 -2.64472,-0.05 -0.579129,-1.520391 -1.783036,-1.053021 -2.847529,-2.097984 -0.704011,-2.01116 -1.285292,1.781059 -1.574039,2.610026 -0.207158,1.966848 -1.41721,3.867459 -0.871534,5.850474 "
                          "0.356102,1.725723 1.013274,3.794028 2.503064,5.108395 1.337578,1.227444 3.193991,1.59596 4.809985,2.332881 2.000696,0.805362 4.318445,1.506767 6.37926,0.477897 2.139744,-0.807015 2.646862,-3.576999 2.229105,-5.580103 -0.206414,-0.773516 -0.792064,-2.738121 0.192311,-0.965724 1.773788,2.539425 0.591052,6.288808 -1.924794,7.854279 -2.955106,2.004348 -4.042819,0.806583 -6.78144,0.138796 -2.315713,-0.436635 -1.637401,-0.41246 -2.057111,-0.49067 z m -7.230402,-8.39485 c 0.152022,-0.213463 0.152022,0.213465 0,0 z m 14.88441,-3.851055 c -0.260012,-1.492645 0.373393,0.657659 0,0 z m -0.306388,-0.654106 c -0.837403,-1.236329 -2.229574,-2.128016 -1.03485,-3.625767 0.01372,-1.826068 0.75338,-3.763535 -0.103622,-5.477179 0.913239,-0.699447 -0.0013,-1.68971 -0.247583,-1.990047 1.973548,0.583877 4.45571,0.273073 5.103432,-2.032355 0.575219,-1.594961 0.612367,1.73726 -0.278273,2.009226 -0.808861,1.635012 1.667161,1.085843 2.014111,0.101379 0.754381,1.553792 2.407003,-0.668987 3.300504,0.108883 1.397772,0.01568 1.664561,-1.245183 1.187622,-1.991887 2.196223,0.84499 2.899836,-1.441701 3.143521,-3.145527 0.447401,-1.382322 0.192067,-2.599225 -0.559677,-3.72842 -0.0044,-0.697918 0.45083,-1.570899 0.180263,-2.017557 1.772352,2.717106 1.384643,6.347347 0.08476,9.193252 -0.870511,2.012511 -3.161569,2.425955 -5.041885,2.84879 -1.423813,-0.444946 -2.70624,0.345491 -4.122825,0.115492 -1.017533,1.274673 -3.565758,-0.403334 -3.492948,1.860543 -0.47435,2.566743 -0.480396,5.255651 0.05051,7.796801 -0.02459,0.0907 -0.20868,0.09548 -0.183047,-0.02563 z m -4.54777,-13.496268 c -0.730094,-1.178498 1.20294,-3.324513 1.874019,-2.826373 -1.116732,0.671118 -1.560152,2.132211 -1.874019,2.826373 z m 13.477783,0.04667 c 0.122576,-0.373769 0.389413,0.107514 0,0 z M 36.52523,18.34228 c -1.058073,-1.988389 -0.52807,-5.081768 1.294915,-6.492979 1.072365,-0.242684 -1.103972,1.79966 -0.910257,2.59368 -0.590428,1.196706 0.01772,3.088285 -0.384658,3.899299 z m -8.491376,-1.23841 c -2.327533,-1.097369 0.992526,-1.0511 1.768832,-0.310537 -0.459012,0.01147 -1.226683,0.627278 -1.768832,0.310537 z M 8.4452604,12.852257 c -2.4341852,-1.000506 1.6650116,-0.324234 2.3110436,-0.932244 2.063808,-0.39015 4.222182,-2.0312478 3.958297,-4.3660884 -0.388366,-1.3693695 0.876572,-1.6914185 0.676193,-0.1614528 0.301677,2.0515677 -0.882857,4.5367922 -3.022981,4.9543802 -1.261802,0.390375 -2.6063946,0.50334 -3.9225526,0.505405 z M 26.385151,11.234572 c -0.869997,0.06922 -2.583879,-1.168883 -0.649798,-0.778912 2.163343,0.311005 3.60503,-1.79988 4.541857,-3.4157669 1.283579,-1.1388173 -0.566925,2.9964489 -1.639505,3.4336829 -0.490661,0.650423 -1.479009,0.877996 -2.252554,0.760996 z m 14.399516,-0.704911 c 0.152022,-0.213463 0.152022,0.213466 0,0 z m -0.359459,-0.42047 c 0.0074,-0.4485096 0.276958,0.357281 0,0 z M 40.115267,9.7160067 c -0.831031,-0.9253188 -0.394571,-1.4808843 0.182052,-0.198113 0.365332,0.1991359 "
                          "-0.08389,0.4317352 -0.182052,0.198113 z M 38.827461,7.876951 c -0.05883,-0.4759524 0.428613,0.3619681 0,0 z M 38.304213,7.4504274 c -1.039752,-1.7655711 1.09713,0.5924089 0,0 z M 21.064149,7.3611741 C 20.132114,5.7255375 21.507666,2.5929177 23.119287,2.668259 21.902147,3.8867082 21.159705,6.1422437 21.064149,7.3611741 z m 7.709323,-6.50060701 c 0.368414,-0.31187496 0.0094,0.30281791 0,0 z"))

(def shape-npc (str "m 45.511455,3.0329195 c -3.273058,-0.1124004 -3.965225,6.4120762 -6.763551,8.1018655 -2.331523,2.099734 -4.75817,-0.288493 -6.738376,-2.1966764 -2.599085,-1.4307458 -0.730192,3.8752864 -0.768456,5.9255074 0.430471,4.646438 0.898757,9.809377 -0.581844,14.115156 -1.331359,2.210386 -3.29077,3.552872 -2.685545,7.323904 -0.565853,3.471403 -2.396835,6.156086 -3.170557,9.688968 -1.013805,3.741825 -2.694653,-0.324098 -1.240438,-2.467583 0.506188,-3.861724 3.840093,-6.437314 2.729072,-10.793594 -1.119811,-1.952556 -4.158967,-2.298904 -5.168172,-0.939647 -0.79242,3.386926 -2.545731,5.635979 -4.2341,7.874642 -0.395108,2.017943 -4.146591,3.526289 -2.734007,0.614611 1.619986,-2.964022 5.002418,-4.456365 5.18559,-8.779709 C 17.632375,28.363665 17.295706,24.191369 17.043658,20.130238 16.094796,16.768099 17.862223,10.56799 15.81641,8.893938 13.111064,9.521301 10.279868,12.233016 7.5783652,10.102128 5.8959465,8.2144525 2.8911087,10.420493 2.3687369,5.7773658 0.76453198,1.7384017 4.2922433,-2.4338663 6.2729487,1.0502391 7.2947983,3.8271066 6.193191,8.9783816 9.2801502,9.3216436 11.454447,9.2832786 13.785716,8.1190571 15.688531,6.4828107 15.457094,4.1036223 11.742218,4.6974251 10.491037,2.35911 8.3241309,-0.1342979 6.8097039,-4.0251621 6.9092733,-8.3230435 6.1784977,-12.184753 7.223172,-16.223265 9.1314169,-18.742966 c 2.2034171,-2.476439 4.9375611,-3.633985 7.5659451,-4.437677 2.551344,-0.477862 5.059563,-1.439726 7.649437,-1.063278 3.130544,0.293804 6.359489,0.591859 9.322846,2.297509 2.679746,2.049563 4.395238,6.270466 5.451386,10.489568 0.140883,4.29052 0.176798,9.3369597 -2.10428,12.39929571 -1.505182,3.05797699 -4.113225,2.81178809 -6.062365,4.64291559 2.038169,2.659785 "
                    "5.107696,6.2473887 7.736947,3.4437847 1.837872,-2.0449497 5.254685,-4.3870389 3.586865,-8.43220985 -0.508657,-4.14482595 3.878175,-4.61491465 3.968258,-0.44922715 0.215119,1.2826623 0.188946,1.855119 -0.735001,2.8852045 z m 6.68e-4,-3.52652514 C 43.672838,-4.2330357 43.233105,3.4338104 45.515034,1.29772 45.631165,0.71920526 45.6324,0.08365839 45.512123,-0.49360564 z M 36.762035,-13.653891 c -1.272628,-5.224641 -4.725354,-8.325987 -8.173489,-8.688535 -2.715248,-0.525736 -5.530278,-1.010588 -7.919257,0.854381 -2.048306,-0.482649 -4.086381,-0.07524 -6.095629,0.62608 -2.772418,1.004876 -6.057736,3.73462 -6.1789844,8.787849 -0.079448,3.590896 -0.2097248,7.7398789 1.5089285,10.5177419 1.3836449,2.6817057 3.4945439,4.1015702 5.6186429,4.5936831 4.126537,1.9540206 8.464797,0.4931719 12.677377,0.3986835 3.049117,-0.4588361 6.765318,-0.8984065 8.492078,-5.5269021 1.059225,-3.2796662 1.393745,-7.7385582 0.237568,-11.0326144 -0.05573,-0.176789 -0.11149,-0.353578 -0.167235,-0.530367 z M 29.878109,17.634061 C 29.339471,13.870111 29.603785,8.177734 27.748623,5.8899228 25.306756,5.5073669 22.8385,6.2591379 20.405705,6.1144932 17.827601,7.160504 18.480684,12.021429 18.390667,15.16851 c 0.05477,4.210976 0.38886,8.404413 0.898715,12.536565 2.223472,2.621879 5.174361,1.429814 7.732268,1.995566 3.198486,-0.607408 3.229883,-6.638032 2.931154,-10.491033 L 29.91759,18.42102 z M 5.1889815,2.4868494 C 3.5569624,-0.97518194 2.4582236,6.1662851 4.5326017,6.7486363 5.4252358,6.1463053 5.48949,3.7434713 5.1889815,2.4868494 z"))

(defn svg-mushroom [x y]
  [:svg.mushroom
   {:key ["mushroom" [x y]] :width 1 :height 1 :viewBox "0 0 48 48" :x x :y y}
   [:path {:d "M 18.393361,22.720865 11.661463,21.398525 6.0114809,17.191088 5.8912677,10.940045 10.0987,4.6889947 12.983803,3.00602 l 3.726581,-1.8031823 7.573384,-0.48085446 5.524607,0.48784206 6.496643,4.0802314 3.606371,3.7265871 1.803187,3.8468022 0.360637,3.005308 -1.202128,3.967014 -2.644672,2.284036 -9.85742,1.202121 -0.841485,6.972323 0.841485,2.284037 3.00531,4.928709 0.601065,3.00531 -0.961701,3.606376 -4.087219,2.40425 -10.338275,-4.808498 -1.803189,-5.890412 z" :fill "#eee" :stroke-width "0.5" :stroke "#000"}]
   [:path {:d "M 18.393361,22.720865 11.661463,21.398525 6.0114809,17.191088 5.8912677,10.940045 10.0987,4.6889947 12.983803,3.00602 l 3.726581,-1.8031823 7.573384,-0.48085446 5.524607,0.48784206 6.496643,4.0802314 3.606371,3.7265871 1.803187,3.8468022 0.360637,3.005308 -1.202128,3.967014 -2.644672,2.284036 -9.85742,1.202121 c -4.844266,0.336233 -3.236912,0.350483 -9.977632,-0.60106 z" :fill "#800000"}]
   [:path {:d shape-mushroom}]])

(defn svg-npc [x y]
  [:svg.npc
   {:key "npc" :width 1 :height 2 :viewBox "0 0 48 48" :x x :y y}
   [:path {:d "m 7.3663362,-8.5668509 0.803683,-7.2558131 3.1142718,-3.943377 6.027624,-2.681496 6.730844,-1.419616 8.13729,1.892821 4.018416,4.416583 2.310588,5.678462 -0.602762,8.2022231 L 36.198465,0.73951859 33.084192,3.4210147 29.266699,4.5251588 30.874065,19.667726 29.065777,30.393712 23.841838,30.709183 18.617899,28.50089 17.512834,8.153067 16.106389,4.0519558 11.18383,2.0013995 7.868638,-4.3080051 z" :fill "#fff"}]
   [:path {:d shape-npc}]
   [:path {:d "M 32.097552,-5.3469955 C 27.223428,6.8404223 25.289886,-20.16221 31.036091,-12.002753 c 0.797878,1.739928 1.514323,4.5599866 1.061461,6.6557575 z M 15.438048,-0.51226798 C 13.274443,-3.4798422 13.517434,-9.2143394 15.236064,-12.493851 c 8.740275,-6.483602 6.341742,18.4344315 0.201984,11.98158302 z" :fill "#000"}]])

(defn rectangle-in-rectangle [r1 r2]
  (let [scale (js/Math.min (/ (first r2) (first r1)) (/ (last r2) (last r1)))]
    (vec (map #(int (* % scale)) r1))))

(defn resize-screen! [c dimensions]
  (reset! c
          (rectangle-in-rectangle dimensions [(.-innerWidth js/window) (.-innerHeight js/window)])))

(defn component-renderer [dimensions db game-map boxes clock]
  (let [player (@db :player)
        npc (@db :npc)
        ratio (/ 2 3)
        w (first dimensions)
        h (last dimensions)]
    [:div
     [:svg#canvas {:width (.-innerWidth js/window)
                   :height (.-innerHeight js/window)
                   :viewBox (str (* (/ w 2) -1) " " (* (/ h 2) -1) " " w " " h)}
      [:g {:transform "scale(4)"}
       (doall
         (for [x (range w) y (range h)]
           [:g {:key ["floor" [x y]]}
            (when (get game-map [x y])
              [:rect {:x (- x (first player) 0.45) :y (* (- y (last player) 0.45) ratio)
                      :width 0.9 :height (* 0.9 ratio)
                      :rx 0.09 :ry 0.06
                      :fill (str "rgba(118, 200, 151," 0.5 ")")}])
            (if (or (= [x y] player)
                    (= [x y] npc)
                    (contains? (set boxes) [x y]))
              [:rect {:key ["shadow" [x y]]
                        :x (- x (first player) 0.35) :y (* (- y (last player) 0.2) ratio)
                        :width 0.6 :height 0.4
                        :rx 0.6 :ry 0.4
                        :fill "rgba(0,0,0,0.3)"}])]))
       (doall
         (for [x (range w) y (range h)]
           (cond (= [x y] npc) (svg-npc (- x (first player) 0.45) (- (* (- y (last player) 0.45) ratio) 1.25 (* (js/Math.sin (* @clock 0.007)) 0.03)))
                 (contains? (set boxes) [x y]) (svg-mushroom (- x (first player) 0.45) (- (* (- y (last player) 0.45) ratio) 0.56)))))
       [:svg#player {:key "player" :width 1 :height 1 :viewBox "0 0 48 48" :x -0.55 :y (- -1.25 (* (js/Math.sin (* @clock 0.005)) 0.03))}
        [:g {}
         [:path {:transform "translate(2,0)" :d "m 44.571428,17.285715 a 20.857143,15.285714 0 1 1 -41.7142865,0 20.857143,15.285714 0 1 1 41.7142865,0 z" :fill "white"}]
         [:path {:transform "translate(2.5714286,0.8571429)" :d "m 33.428573,37.285713 a 10.142858,8.1428566 0 1 1 -20.285715,0 10.142858,8.1428566 0 1 1 20.285715,0 z" :fill "white"}]
         [:path {:d shape-player}]]]]]]))

