package com.example.task.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agency {
  public String fullName;

  public String shortName;

  public String agencyInn;

  public String agencyKpp;

  public AgencyTofk agencyTofk;

  public String srCode;

  public String gmuCode;

  public AgencyPpo agencyPpo;

  public AgencyOpf agencyOpf;

  public AgencyScope[] agencyScopes;
}
