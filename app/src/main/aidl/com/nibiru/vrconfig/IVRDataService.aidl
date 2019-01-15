package com.nibiru.vrconfig;


interface IVRDataService{

boolean hasGameConfig(String name);

boolean updateVRConfig();

String getVRVersion();

String getChannel();

boolean isDistortion();

boolean setHwcComposePolicy(int val);

String getStoreChannel();

boolean isStoreOversea();

}