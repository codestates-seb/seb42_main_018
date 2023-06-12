import { describe, test, expect } from '@jest/globals';
import { checkEmail, checkPassword } from '../util/authorization/checkPassword';

describe('이메일 주소 유효성 검사', () => {
  test('@가 없는 이메일 주소는 유효하지 않은 이메일 주소입니다.', () => {
    const email = 'kimcoding.com';
    const result = checkEmail(email);
    expect(result).toBe(false);
  });
  test('.가 없는 이메일 주소는 유효하지 않은 이메일 주소입니다.', () => {
    const email = 'kimcoding@xoxo';
    const result = checkEmail(email);
    expect(result).toBe(false);
  });
  test('. 기호 뒤에 알파벳이 없는 이메일 주소는 유효하지 않은 이메일 주소입니다.', () => {
    const email = 'kimcoding@xoxo.';
    const result = checkEmail(email);
    expect(result).toBe(false);
  });
  test('@는 한 개, .는 한 개 이상 존재하며, 기호 뒤에 알파벳이 있는 이메일 주소는 유효한 이메일 주소입니다.', () => {
    const email = 'kimcoding@xoxo.co.kr';
    const result = checkEmail(email);
    expect(result).toBe(true);
  });
});

describe('비밀번호 유효성 검사', () => {
  test('8~20자 사이를 벗어난 비밀번호는 유효하지 않습니다. (1)', () => {
    const password = '12345';
    const result = checkPassword(password);
    expect(result).toBe(false);
  });
  test('8~20자 사이를 벗어난 비밀번호는 유효하지 않습니다. (2)', () => {
    const password = '1234567890123456789012';
    const result = checkPassword(password);
    expect(result).toBe(false);
  });
  test('영문 알파벳과 숫자를 최소 한 개 이상 포함하지 않는 비밀번호는 유효하지 않습니다. (1)', () => {
    const password = '1234567890123456';
    const result = checkPassword(password);
    expect(result).toBe(false);
  });
  test('영문 알파벳과 숫자를 최소 한 개 이상 포함하지 않는 비밀번호는 유효하지 않습니다. (2)', () => {
    const password = 'abcdefghijk';
    const result = checkPassword(password);
    expect(result).toBe(false);
  });
  test('비밀번호는 영문 알파벳과 숫자를 최소 한 개 이상 포함하여 8~20자 사이여야 합니다.', () => {
    const password = '1234qwer';
    const result = checkPassword(password);
    expect(result).toBe(true);
  });
  test('영문 알파벳과 숫자를 최소 한 개 이상 포함하여 8~20자 사이의 비밀번호라면 특수문자 포함 여부는 체크하지 않습니다.', () => {
    const password = '1234qwer!';
    const result = checkPassword(password);
    expect(result).toBe(true);
  });
});
