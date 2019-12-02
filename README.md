### Overview
대한민국에서 사업자번호로 사업의 상태 -사업중,휴업,폐업- 등을 확인할 수 있는 API

### Getting Started
```groovy
repositories {
    maven { url = uri("https://raw.githubusercontent.com/freeism/businessRegistrationFinder/master/releases") }
}

dependencies {
    implementation 'io.github.freeism:business-registration-finder:1.0.1.RELEASE'
}
```

```java
@BRNFinderEnabled
@Configuation
public class FooConfig {
}
```

```java
public class FooClass {
    @Autowired
    private BusinessRegistrationFinder finder;
    
    public BusinessRegistration foo(String number) {
        return finder.find(number);
    }
}
```

### 사업자 구분

* 개인사업자
  * 과세사업자
    * 일반과세사업자
    * 간이과세사업자
  * 면세사업자
* 법인사업자
  * 과세사업자
  * 면세사업자
  
### 사업자등록번호의 체계

* 형식 : 000-00-00000

* 맨 앞 세자리 숫자의 의미 : [관할 세무서코드](https://www.nts.go.kr/info/info_04.asp?minfoKey=MINF8320080211205953&mbsinfoKey=MBS20080308131447188&type=V)
  * 너무 오래된 데이터라서 신규 세무서코드는 없는 듯 (세상에나 2010년 자료라니)

* 가운데 두자리 숫자의 의미
  * 01 ~ 79 : 개인사업자(과세)
  * 80 : 원천징수의무가 있는 비사업자 및 다단계판매원 (+아파트관리사무소)
  * 81, 86, 87, 88 : 영리법인의 본점
  * 82 : 비영리법인의 본점 및 지점 (사단, 재단, 기타 단체)
  * 83 : 국가, 자방자치단체, 지방자치단체조합
  * 84 : 외국법인의 본점 및 지점
  * 85 : 영리법인의 지점
  * 89 : 종교단체
  * 90 ~ 99 : 개인사업자(면세)

* 마지막 다섯자리 숫자의 의미
  * 등록 또는 지정일자 순으로 0001 ~ 9999를 부여함
  * 마지막 자리는 검증비트 : 0 ~ 9

* 예시
  * 쿠팡 : 120-88-00767
    * 120 : 서울 삼성동
    * 88 : 영리법인의 본점
    * 0076 : 등록번호
    * 7 : 검증비트
  * 삼성전자 : 124-81-00998
    * 124 : 수원
    * 81 : 영리법인의 본점
    * 0099 : 등록번호
    * 8 : 검증비트
  * 롯데면세점 : 104-85-18866
    * 104 : 남대문
    * 85 : 영리법인의 지점 (면세사업자는 아니네, 법인이라서..)
    * 1886 : 등록번호
    * 6 : 검증비트
    
* 이슈
  * 최근에는 번호체계가 바뀐 것 같은데?
    * 527-88-00686 (카카오페이) : 527 에 해당하는 코드가 없...
    * 793-85-00297 (신세계면세점) : 793 도 없...
