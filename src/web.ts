import { WebPlugin } from '@capacitor/core';

import type { CapacitorPhoneHintPlugin } from './definitions';

export class CapacitorPhoneHintWeb extends WebPlugin implements CapacitorPhoneHintPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
