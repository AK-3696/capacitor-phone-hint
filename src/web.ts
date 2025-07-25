// capacitor-phone-hint/src/web.ts
import { WebPlugin } from '@capacitor/core';

export class CapacitorPhoneHintWeb extends WebPlugin {
  async getPhoneNumber(): Promise<{ phoneNumber: string }> {
    return { phoneNumber: "" };
  }
}
