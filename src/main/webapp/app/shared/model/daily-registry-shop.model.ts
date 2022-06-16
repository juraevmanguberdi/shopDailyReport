export interface IDailyRegistryShop {
  id?: number;
  today?: Date;
  sales?: number;
  goods?: number;
  cashShop?: number | null;
  click?: number | null;
  terminal?: number | null;
  debtReturn?: number | null;
  debtGiven?: number | null;
  expense?: number | null;
  balance?: number | null;
  code?: string | null;
}

export class DailyRegistryShop implements IDailyRegistryShop {
  constructor(
    public id?: number,
    public today?: Date,
    public sales?: number,
    public goods?: number,
    public cashShop?: number | null,
    public click?: number | null,
    public terminal?: number | null,
    public debtReturn?: number | null,
    public debtGiven?: number | null,
    public expense?: number | null,
    public balance?: number | null,
    public code?: string | null
  ) {}
}
