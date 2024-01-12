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
public class AuthorAgency {
  private String fullName;
  private String shortName;
  private String agencyInn;
  private String agencyKpp;
  private String srCode;
  private String gmuCode;
}
