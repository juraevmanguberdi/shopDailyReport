import { IClient } from '@/shared/model/client.model';
import { IPaymentMethod } from '@/shared/model/payment-method.model';

export interface IDebtReturn {
  id?: number;
  returnAmount?: number;
  returnDate?: Date;
  code?: string | null;
  client?: IClient;
  paymentMethod?: IPaymentMethod;
}

export class DebtReturn implements IDebtReturn {
  constructor(
    public id?: number,
    public returnAmount?: number,
    public returnDate?: Date,
    public code?: string | null,
    public client?: IClient,
    public paymentMethod?: IPaymentMethod
  ) {}
}
