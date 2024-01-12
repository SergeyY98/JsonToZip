package com.example.task.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgencyScopesAndOkved {
  private String agencyScopesCode;
  private String agencyScopesName;
  private String economicActivityTypeCode;
  private String economicActivityTypeName;
}
