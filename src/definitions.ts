export interface CapacitorPhoneHintPlugin {
  getPhoneNumber(): Promise<{ phoneNumber: string }>;
}
