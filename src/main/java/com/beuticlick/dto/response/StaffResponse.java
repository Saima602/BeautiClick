package com.beuticlick.dto.response;

import com.beuticlick.constant.StaffRole;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StaffResponse {
	private Long id;
	private String name;
	private String phone;
	private String email;
	private StaffRole role;
	private Boolean available;
}
