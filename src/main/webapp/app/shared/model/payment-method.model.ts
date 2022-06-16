export interface IPaymentMethod {
  id?: number;
  name?: string;
  code?: string | null;
  note?: string | null;
}

export class PaymentMethod implements IPaymentMethod {
  constructor(public id?: number, public name?: string, public code?: string | null, public note?: string | null) {}
}
