import Foundation

@objc public class CapacitorPhoneHint: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
