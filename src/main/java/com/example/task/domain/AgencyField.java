package com.example.task.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgencyField {
  private Agency agency;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  public Date regDate;

  public AgencyAddress agencyAddress;

  public Email[] authAgencyEmails;

  public Phone[] authAgencyPhones;

  public AgencyScopesAndOkved[] agencyScopesAndOkvedList;

  public Oktmo agencyOktmo;
}
