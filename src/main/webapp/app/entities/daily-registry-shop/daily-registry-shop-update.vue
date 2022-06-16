<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.dailyRegistryShop.home.createOrEditLabel"
          data-cy="DailyRegistryShopCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.dailyRegistryShop.home.createOrEditLabel')"
        >
          Create or edit a DailyRegistryShop
        </h2>
        <div>
          <div class="form-group" v-if="dailyRegistryShop.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="dailyRegistryShop.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.dailyRegistryShop.today')" for="daily-registry-shop-today"
              >Today</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="daily-registry-shop-today"
                  v-model="$v.dailyRegistryShop.today.$model"
                  name="today"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="daily-registry-shop-today"
                data-cy="today"
                type="text"
                class="form-control"
                name="today"
                :class="{ valid: !$v.dailyRegistryShop.today.$invalid, invalid: $v.dailyRegistryShop.today.$invalid }"
                v-model="$v.dailyRegistryShop.today.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.dailyRegistryShop.today.$anyDirty && $v.dailyRegistryShop.today.$invalid">
              <small class="form-text text-danger" v-if="!$v.dailyRegistryShop.today.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.dailyRegistryShop.sales')" for="daily-registry-shop-sales"
              >Sales</label
            >
            <input
              type="number"
              class="form-control"
              name="sales"
              id="daily-registry-shop-sales"
              data-cy="sales"
              :class="{ valid: !$v.dailyRegistryShop.sales.$invalid, invalid: $v.dailyRegistryShop.sales.$invalid }"
              v-model.number="$v.dailyRegistryShop.sales.$model"
              required
            />
            <div v-if="$v.dailyRegistryShop.sales.$anyDirty && $v.dailyRegistryShop.sales.$invalid">
              <small class="form-text text-danger" v-if="!$v.dailyRegistryShop.sales.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.dailyRegistryShop.sales.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.dailyRegistryShop.goods')" for="daily-registry-shop-goods"
              >Goods</label
            >
            <input
              type="number"
              class="form-control"
              name="goods"
              id="daily-registry-shop-goods"
              data-cy="goods"
              :class="{ valid: !$v.dailyRegistryShop.goods.$invalid, invalid: $v.dailyRegistryShop.goods.$invalid }"
              v-model.number="$v.dailyRegistryShop.goods.$model"
              required
            />
            <div v-if="$v.dailyRegistryShop.goods.$anyDirty && $v.dailyRegistryShop.goods.$invalid">
              <small class="form-text text-danger" v-if="!$v.dailyRegistryShop.goods.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.dailyRegistryShop.goods.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.dailyRegistryShop.cashShop')"
              for="daily-registry-shop-cashShop"
              >Cash Shop</label
            >
            <input
              type="number"
              class="form-control"
              name="cashShop"
              id="daily-registry-shop-cashShop"
              data-cy="cashShop"
              :class="{ valid: !$v.dailyRegistryShop.cashShop.$invalid, invalid: $v.dailyRegistryShop.cashShop.$invalid }"
              v-model.number="$v.dailyRegistryShop.cashShop.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.dailyRegistryShop.click')" for="daily-registry-shop-click"
              >Click</label
            >
            <input
              type="number"
              class="form-control"
              name="click"
              id="daily-registry-shop-click"
              data-cy="click"
              :class="{ valid: !$v.dailyRegistryShop.click.$invalid, invalid: $v.dailyRegistryShop.click.$invalid }"
              v-model.number="$v.dailyRegistryShop.click.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.dailyRegistryShop.terminal')"
              for="daily-registry-shop-terminal"
              >Terminal</label
            >
            <input
              type="number"
              class="form-control"
              name="terminal"
              id="daily-registry-shop-terminal"
              data-cy="terminal"
              :class="{ valid: !$v.dailyRegistryShop.terminal.$invalid, invalid: $v.dailyRegistryShop.terminal.$invalid }"
              v-model.number="$v.dailyRegistryShop.terminal.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.dailyRegistryShop.debtReturn')"
              for="daily-registry-shop-debtReturn"
              >Debt Return</label
            >
            <input
              type="number"
              class="form-control"
              name="debtReturn"
              id="daily-registry-shop-debtReturn"
              data-cy="debtReturn"
              :class="{ valid: !$v.dailyRegistryShop.debtReturn.$invalid, invalid: $v.dailyRegistryShop.debtReturn.$invalid }"
              v-model.number="$v.dailyRegistryShop.debtReturn.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.dailyRegistryShop.debtGiven')"
              for="daily-registry-shop-debtGiven"
              >Debt Given</label
            >
            <input
              type="number"
              class="form-control"
              name="debtGiven"
              id="daily-registry-shop-debtGiven"
              data-cy="debtGiven"
              :class="{ valid: !$v.dailyRegistryShop.debtGiven.$invalid, invalid: $v.dailyRegistryShop.debtGiven.$invalid }"
              v-model.number="$v.dailyRegistryShop.debtGiven.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.dailyRegistryShop.expense')" for="daily-registry-shop-expense"
              >Expense</label
            >
            <input
              type="number"
              class="form-control"
              name="expense"
              id="daily-registry-shop-expense"
              data-cy="expense"
              :class="{ valid: !$v.dailyRegistryShop.expense.$invalid, invalid: $v.dailyRegistryShop.expense.$invalid }"
              v-model.number="$v.dailyRegistryShop.expense.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.dailyRegistryShop.balance')" for="daily-registry-shop-balance"
              >Balance</label
            >
            <input
              type="number"
              class="form-control"
              name="balance"
              id="daily-registry-shop-balance"
              data-cy="balance"
              :class="{ valid: !$v.dailyRegistryShop.balance.$invalid, invalid: $v.dailyRegistryShop.balance.$invalid }"
              v-model.number="$v.dailyRegistryShop.balance.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.dailyRegistryShop.code')" for="daily-registry-shop-code"
              >Code</label
            >
            <input
              type="text"
              class="form-control"
              name="code"
              id="daily-registry-shop-code"
              data-cy="code"
              :class="{ valid: !$v.dailyRegistryShop.code.$invalid, invalid: $v.dailyRegistryShop.code.$invalid }"
              v-model="$v.dailyRegistryShop.code.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.dailyRegistryShop.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./daily-registry-shop-update.component.ts"></script>
