<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.debtReturn.home.createOrEditLabel"
          data-cy="DebtReturnCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.debtReturn.home.createOrEditLabel')"
        >
          Create or edit a DebtReturn
        </h2>
        <div>
          <div class="form-group" v-if="debtReturn.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="debtReturn.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.debtReturn.returnAmount')" for="debt-return-returnAmount"
              >Return Amount</label
            >
            <input
              type="number"
              class="form-control"
              name="returnAmount"
              id="debt-return-returnAmount"
              data-cy="returnAmount"
              :class="{ valid: !$v.debtReturn.returnAmount.$invalid, invalid: $v.debtReturn.returnAmount.$invalid }"
              v-model.number="$v.debtReturn.returnAmount.$model"
              required
            />
            <div v-if="$v.debtReturn.returnAmount.$anyDirty && $v.debtReturn.returnAmount.$invalid">
              <small class="form-text text-danger" v-if="!$v.debtReturn.returnAmount.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.debtReturn.returnAmount.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.debtReturn.returnDate')" for="debt-return-returnDate"
              >Return Date</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="debt-return-returnDate"
                  v-model="$v.debtReturn.returnDate.$model"
                  name="returnDate"
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
                id="debt-return-returnDate"
                data-cy="returnDate"
                type="text"
                class="form-control"
                name="returnDate"
                :class="{ valid: !$v.debtReturn.returnDate.$invalid, invalid: $v.debtReturn.returnDate.$invalid }"
                v-model="$v.debtReturn.returnDate.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.debtReturn.returnDate.$anyDirty && $v.debtReturn.returnDate.$invalid">
              <small class="form-text text-danger" v-if="!$v.debtReturn.returnDate.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.debtReturn.code')" for="debt-return-code">Code</label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="debt-return-code"
              data-cy="code"
              :class="{ valid: !$v.debtReturn.code.$invalid, invalid: $v.debtReturn.code.$invalid }"
              v-model="$v.debtReturn.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.debtReturn.client')" for="debt-return-client">Client</label>
            <select class="form-control" id="debt-return-client" data-cy="client" name="client" v-model="debtReturn.client" required>
              <option v-if="!debtReturn.client" v-bind:value="null" selected></option>
              <option
                v-bind:value="debtReturn.client && clientOption.id === debtReturn.client.id ? debtReturn.client : clientOption"
                v-for="clientOption in clients"
                :key="clientOption.id"
              >
                {{ clientOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.debtReturn.client.$anyDirty && $v.debtReturn.client.$invalid">
            <small class="form-text text-danger" v-if="!$v.debtReturn.client.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.debtReturn.paymentMethod')" for="debt-return-paymentMethod"
              >Payment Method</label
            >
            <select
              class="form-control"
              id="debt-return-paymentMethod"
              data-cy="paymentMethod"
              name="paymentMethod"
              v-model="debtReturn.paymentMethod"
              required
            >
              <option v-if="!debtReturn.paymentMethod" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  debtReturn.paymentMethod && paymentMethodOption.id === debtReturn.paymentMethod.id
                    ? debtReturn.paymentMethod
                    : paymentMethodOption
                "
                v-for="paymentMethodOption in paymentMethods"
                :key="paymentMethodOption.id"
              >
                {{ paymentMethodOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.debtReturn.paymentMethod.$anyDirty && $v.debtReturn.paymentMethod.$invalid">
            <small class="form-text text-danger" v-if="!$v.debtReturn.paymentMethod.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
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
            :disabled="$v.debtReturn.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./debt-return-update.component.ts"></script>
