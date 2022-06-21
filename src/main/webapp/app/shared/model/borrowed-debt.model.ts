import { IBorrowedDebtType } from '@/shared/model/borrowed-debt-type.model';

export interface IBorrowedDebt {
  id?: number;
  amount?: number;
  notes?: string | null;
  code?: string | null;
  date?: Date | null;
  borrowedDebtType?: IBorrowedDebtType;
}

export class BorrowedDebt implements IBorrowedDebt {
  constructor(
    public id?: number,
    public amount?: number,
    public notes?: string | null,
    public code?: string | null,
    public date?: Date | null,
    public borrowedDebtType?: IBorrowedDebtType
  ) {}
}
